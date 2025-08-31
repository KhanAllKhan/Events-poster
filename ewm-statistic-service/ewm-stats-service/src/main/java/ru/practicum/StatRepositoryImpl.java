package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.dto.ViewsStatsRequest;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatRepositoryImpl implements StatRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void saveHit(EndpointHit hit) {
        String sql = """
            INSERT INTO stats (app, uri, ip, date)
            VALUES (:app, :uri, :ip, :timestamp)
            """;

        var params = new MapSqlParameterSource()
                .addValue("app", hit.getApp())
                .addValue("uri", hit.getUri())
                .addValue("ip", hit.getIp())
                .addValue("timestamp", Timestamp.valueOf(hit.getTimestamp()));

        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<ViewStats> getStats(ViewsStatsRequest request) {
        return queryStats(request, "COUNT(ip)");
    }

    @Override
    public List<ViewStats> getUniqueStats(ViewsStatsRequest request) {
        return queryStats(request, "COUNT(DISTINCT ip)");
    }

    /**
     * Вспомогательный метод для обоих запросов:
     * формирует SQL с динамическим COUNT-выражением,
     * опциональным условием по списку uris и limit.
     */
    private List<ViewStats> queryStats(ViewsStatsRequest req, String countExpr) {
        // 1. Базовый SQL с подстановкой countExpr
        String base = String.format(
                "SELECT app, uri, %s AS hits " +
                        "FROM stats " +
                        "WHERE date BETWEEN :start AND :end ",
                countExpr
        );

        var sql = new StringBuilder(base);
        var params = new MapSqlParameterSource()
                .addValue("start", Timestamp.valueOf(req.getStart()))
                .addValue("end",   Timestamp.valueOf(req.getEnd()));

        // 2. Добавляем условие IN (:uris), если список задан
        if (req.getUris() != null && !req.getUris().isEmpty()) {
            sql.append("AND uri IN (:uris) ");
            params.addValue("uris", req.getUris());
        }

        // 3. Группировка и сортировка
        sql.append("GROUP BY app, uri ")
                .append("ORDER BY hits DESC ");

        // 4. Ограничение количества строк, если есть limit
        if (req.hasLimitCondition()) {
            sql.append("LIMIT :limit");
            params.addValue("limit", req.getLimit());
        }

        // 5. Запускаем запрос и мапим в DTO
        return jdbcTemplate.query(
                sql.toString(),
                params,
                (rs, rowNum) -> new ViewStats(
                        rs.getString("app"),
                        rs.getString("uri"),
                        rs.getLong("hits")
                )
        );
    }
}
