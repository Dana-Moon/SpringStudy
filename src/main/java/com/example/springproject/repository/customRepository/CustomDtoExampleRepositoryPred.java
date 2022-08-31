//package com.example.springproject.repository.customRepository;
//
//import com.example.springproject.entity.CustomDto.CustomDtoSortPages;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface CustomDtoExampleRepositoryPred extends JpaRepository<CustomDtoSortPages, String> {
//    /*
//    LAG(SEQ, 1, 0)
//    LAG : 이전값을 찾는 ORACLE 함수
//    (SEQ, 1, 0) = ([조회하는컬럼], [몇번째 이전 값을 찾는지], [이전값이 없을 경우])
//    () 괄호 안에 있는 SELECT 결과물을 하나의 테이블로 인식하고, () 괄호 밖에 있는 SELECT 문과 WHERE 절을 통해 결과값을 도출
//     */
//    @Query (value = "SELECT * FROM (SELECT SEQ,"
//                    + "LAG(SEQ,1,0) OVER(ORDER BY SEQ DESC) PREVID,"
//                    + "LAG(TITLE,1,'이전글이 없습니다.) OVER(ORDER BY ID DESC) PREV_SUBJECT,"
//                    + "LEAD(SEQ,1,0) OVER(ORDER BY SEQ DESC) NEXTID,"
//                    + "LEAD(TITLE,1,'다음글이 없습니다') OVER(ORDER BY ID DESC) NEXT_SUBJECT"
//                    + "FROM BOARD"
//                    + ") WHERE SEQ = :seq",
//            nativeQuery = true)
//    CustomDtoSortPages findByPages(Long seq);
//}
