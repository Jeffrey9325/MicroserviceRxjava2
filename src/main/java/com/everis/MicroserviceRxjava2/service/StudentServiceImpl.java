package com.everis.MicroserviceRxjava2.service;

import com.everis.MicroserviceRxjava2.model.Students;
import com.everis.MicroserviceRxjava2.repository.ReactiveRepository;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * StudentServiceImpl class.
 * @author jeffrey
 * @version v1.0
 */

@Service
public class StudentServiceImpl implements IstudentsService {
  /**
   * Reactive Repository.
   */
  @Autowired
  private ReactiveRepository repository;
 
  
  @Override
  public Flowable<Students> searchbyName(final String name) {
    return repository.findByFullName(name);
  }

  @Override
  public Single<Students> searchbyDocument(final String document) {
    return repository.findByDocumentNumber(document);
  }

  @Override
  public Flowable<Students> searchbyrankdateofBirth(final Date fromDate, final Date toDate) {
    return repository.findByDateofBirthBetween(fromDate, toDate);
  }

  @Override
  public Single<Students> createStudent(final Students student) {
    return repository.save(student);
  }

  @Override
  public Flowable<Students> allStudents() {
    return repository.findAll();
  }

  @Override
  public Single<Students> modifyStudent(final Students student) {
    return repository.save(student);
  }

  @Override
  public Completable deleteStudents(final Students student) {
    return repository.delete(student);
  }

  @Override
  public Single<Students> findbyId(final String idStudent) {
    return repository.findById(idStudent);
  }
}
