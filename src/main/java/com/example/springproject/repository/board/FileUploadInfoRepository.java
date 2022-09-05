package com.example.springproject.repository.board;

import com.example.springproject.entity.data.FileUploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//FileUploadEntity를 저장하는 인터페이스 (JPA CrudRepository)
public interface FileUploadInfoRepository extends JpaRepository<FileUploadEntity, Long> {

    //findBy : 튜플을 찾겠다
    //BoardSeq : BoardSeq 컬럼에 데이터를 찾겠다
    FileUploadEntity findByBoardSeq(Long seq);
}
