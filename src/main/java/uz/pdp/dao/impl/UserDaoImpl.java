package uz.pdp.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import uz.pdp.dao.UserDao;
import uz.pdp.model.User;
import uz.pdp.rowmapper.UserRowMapper;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Override
    public User save(User user) {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO users(username, email, password, gender) VALUES (?, ? , ? , ?)",
                    new String[]{"id", "username", "email", "password", "gender"}
            );
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getGender());
            return preparedStatement;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
//        user.setId(keyHolder.getKey().longValue());
        /*jdbcTemplate.update(
                "INSERT INTO users(username, email, password, gender) VALUES (?, ? , ? , ?) ",
                user.getUsername(), user.getEmail(), user.getPassword(), user.getGender());*/
        Map<String, Object> keys = keyHolder.getKeys();
        keys.forEach((k, v) -> System.out.println(k + " : " + v));
        user.setId((Long) keys.get("id"));
        user.setUsername((String) keys.get("username"));
        return user;
    }

    public User saveBySimpleInsert(User user) {
        KeyHolder keyHolder = simpleJdbcInsert.withTableName("users")
                .usingGeneratedKeyColumns("id", "created_time")
//                .usingColumns("username", "email", "password", "gender")
//                .execute(new BeanPropertySqlParameterSource(user));
                .executeAndReturnKeyHolder(new BeanPropertySqlParameterSource(user));
//                .executeAndReturnKey(new BeanPropertySqlParameterSource(user));
        Map<String, Object> keys = keyHolder.getKeys();
        user.setId((Long) keys.get("id"));
        user.setUsername(((Date) keys.get("created_time")).toString());
        return user;
    }

    @Override
    public User findByEmail(String email) {
        return namedJdbcTemplate.queryForObject(
                "SELECT * FROM users where email=:email",
                Map.of("email", email),
                User.class
        );
    }

    public User saveByNamedParameter(User user) {
        /*namedJdbcTemplate.update(
                "INSERT INTO users(username, email, password, gender) VALUES (:username, :email, :password ,:gender)",
                Map.of(
                        "username", user.getUsername(),
                        "email", user.getEmail(),
                        "password", user.getPassword(),
                        "gender", user.getGender()
                )
        );
     */
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(
                "INSERT INTO users(username, email, password, gender) VALUES (:username, :email, :password ,:gender)",
                parameterSource,
                keyHolder,
                new String[]{"id"}
        );
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    public User getByIdNamedParameter(Long id) {
        User user = namedJdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = :id",
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(User.class)
        );
        return user;
    }

    @Override
    public User getById(Long id) {
       /* BeanPropertyRowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
        User user = jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                rowMapper,
                id
        );*/
        /*RowMapper<User> rowMapper = (rs, rowNumber) -> User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .gender(rs.getString("gender"))
                .createdDate(rs.getDate("created_time"))
                .build();
        User user = jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                rowMapper,
                id
        );*/

        User user = jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                userRowMapper,
                id
        );
        return user;
    }

    @Override
    public User update(User user) {
        int updated = jdbcTemplate.update(
                " UPDATE users SET username=?, email=?, password=?, gender=? WHERE id= ?",
                user.getUsername(), user.getEmail(), user.getPassword(), user.getGender(), user.getId()
        );
        return user;
    }

    @Override
    public boolean delete(Long id) {
        int updated = jdbcTemplate.update(
                "DELETE FROM users WHERE id = ?",
                id
        );
        return updated == 1;
    }
}
