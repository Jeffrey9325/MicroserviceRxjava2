package com.everis.MicroserviceRxjava2.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.MicroserviceRxjava2.service.StudentServiceImpl;
import com.everis.MicroserviceRxjava2.model.Students;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Students/v1.0")
public class Controller {
	
	@Autowired
	public StudentServiceImpl repository;
	
	@GetMapping("/names/{fullName}")
	  public Flowable<Students> searchbyName(@PathVariable final String fullName) {
	    return repository.searchbyName(fullName);
	  }
	
	@GetMapping("/documents/{document}")
	  public Single<Students> searchbyDocument(@PathVariable final String document) {
	    return repository.searchbyDocument(document);
	  }
	
	@GetMapping("/dates/{fromDate}/{toDate}")
	  public Flowable<Students> searchbyrankdateofBirth(
	      @PathVariable @DateTimeFormat(iso = ISO.DATE) final Date fromDate,
	      @PathVariable  @DateTimeFormat(iso = ISO.DATE)  final Date toDate) {
	    return repository.searchbyrankdateofBirth(fromDate, toDate);
	  }
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	  public Single<Students> createStudent(@Valid @RequestBody final Students student) {
	    return repository.createStudent(student)
	    		.doOnSuccess(s -> System.out.println("Finished"))
	    		.doOnError(e -> System.err.println("error"))
	    		.subscribeOn(Schedulers.io());
	  }
	
	@GetMapping("/")
	  public Flowable<Students> allStudents() {  
	    return repository.allStudents();
	  }
	
	@PutMapping("/{id}")
	  public Single<Students> modifyStudent(@PathVariable final String id,
	      @Valid @RequestBody final Students student) {
	    return repository.findbyId(id)
	    .flatMap(people -> {
	      people.setId(id);
	      people.setFullName(student.getFullName());
	      people.setGender(student.getGender());
	      people.setDateofBirth(student.getDateofBirth());
	      people.setTypeDocument(student.getTypeDocument());
	      people.setDocumentNumber(student.getDocumentNumber());
	      return repository.modifyStudent(people);
	    })
	    .doOnSuccess(s -> System.out.println("Finished modify Students"))
		.doOnError(e -> System.err.println("error modify Students"))
		.subscribeOn(Schedulers.io());
	  }
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	  public Completable deleteStudents(@PathVariable final String id) {
	    return repository.findbyId(id)
	    		.flatMapCompletable(people ->
          repository.deleteStudents(people))
	    		.doOnSubscribe(s -> System.out.println("finished delete"))
	    		.doOnError(e -> System.out.println("Error delete"))
	    		.subscribeOn(Schedulers.io());	
		
	
	  }

}
