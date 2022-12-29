package com.BuiHuuThong.MyTravel.repository;

import com.BuiHuuThong.MyTravel.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT * FROM TaiKhoan WHERE Email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT ISNULL((SELECT TOP 1 1 FROM TaiKhoan WHERE Email = :email), 0)")
    boolean existsByEmail(String email);

    @Query("""
		SELECT *
		FROM TaiKhoan
		WHERE
			(:timKiem IS NULL OR
				Email LIKE '%' + :timKiem + '%' OR
				FullName LIKE '%' + :timKiem + '%') AND
			(IsDelete = 0)""")
    List<User> findAllWithSearch(String timKiem);

    @Query("""
		SELECT *
		FROM (
			SELECT TOP((:page + 1) * :size)
				ROW_NUMBER() OVER (ORDER BY UserId DESC) AS RowNum,
				*
			FROM TaiKhoan
			WHERE
				(:timKiem IS NULL OR
                    Email LIKE '%' + :timKiem + '%' OR
                    FullName LIKE '%' + :timKiem + '%') AND
				(IsDelete = 0)
		) AS Temp
		WHERE Temp.RowNum > :page * :size""")
    List<User> findAllWithSearchFilterPagination(
            String timKiem,
            int page,
            int size
    );

    @Query("""
		SELECT COUNT(*)
		FROM TaiKhoan
		WHERE
			(:timKiem IS NULL OR
                    Email LIKE '%' + :timKiem + '%' OR
                    FullName LIKE '%' + :timKiem + '%') AND
			(IsDelete = 0)""")
    long countWithSearchFilterPagination(String timKiem);
}