package com.everis.MicroserviceRxjava2.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.everis.MicroserviceRxjava2.model.Students;
import com.everis.MicroserviceRxjava2.service.IstudentsService;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)

public class ControllerTest {

	@MockBean
	Controller restControllerStudent;
	
	@Mock
	IstudentsService istudentsService;
	
	@Mock
	Students students;
	
//	@Before
//	public void init() {
//		this.buildStudents();
//	}
		
	@Test
	public void allStudents() {
		//System.out.println(istudentsService.allStudents());
		when(restControllerStudent.allStudents())
		.thenReturn(Flowable.just(Mockito.mock(Students.class)));
		
//		TestObserver testObserver = new TestObserver();
//		restControllerStudent.allStudents().subscribe();
//	    testObserver.onComplete();
	}
	
	@Test
	public void createStudent() {
		
		Students one = new Students("st", "Test1", "male", LocalDate.of(1993, 02, 1), "dni", "16754356");
		
		when(restControllerStudent.createStudent(one))
		.thenReturn(Single.just(one));
	}
	
	private void buildStudents() {
		
		Students students = new Students();
		students.setId("12345");
		students.setFullName("jeff");
		students.setGender("male");
		students.setDateofBirth(LocalDate.of(1993, 02, 25));
		students.setTypeDocument("dni");
		students.setDocumentNumber("2467546");
		
	}

}
