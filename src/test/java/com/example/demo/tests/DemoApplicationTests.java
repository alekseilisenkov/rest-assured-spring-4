package com.example.demo.tests;

import com.example.demo.domain.StudentInfo;
import com.example.demo.helpers.EndPoints;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.demo.specs.RequestSpecs.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;

@Tag("Test")
@SpringBootTest
class DemoApplicationTests extends TestBase {

    @Test
    void addNewStudent() {
        given()
                .spec(spec)
                .body(getDataAdd())
                .when()
                .post(EndPoints.CONTEXT_PATH_STUDENT)
                .then()
                .spec(specCreated)
                .body("student_name", is("Ginny Weasley"))
                .body("student_house", is("Gryffindor"));
    }

    @Test
    void getAllStudents() {
        createStudent("James Potter", "Gryffindor");
        List<StudentInfo> students = given()
                .spec(spec)
                .when()
                .get(EndPoints.CONTEXT_PATH_ALL_STUDENTS)
                .then()
                .spec(specSuccess)
                .extract().jsonPath().getList(".", StudentInfo.class);
        assertThat(students.size(), Matchers.greaterThan(0));
    }

    @Test
    void getStudentsByHouse() {
        createStudent("Harry Potter", "Gryffindor");
        createStudent("Draco Malfoy", "Slytherin");
        List<StudentInfo> students = given()
                .params("housename", "Gryffindor")
                .spec(spec)
                .when()
                .get(EndPoints.CONTEXT_PATH_HOUSE)
                .then()
                .spec(specSuccess)
                .body("student_name.flatten()", hasItem("Harry Potter"))
                .extract().jsonPath().getList(".", StudentInfo.class);
        assertThat(students.size(), Matchers.greaterThan(0));
    }

    @Test
    void searchStudentByName() {
        createStudent("Hermione Granger", "Gryffindor");
        createStudent("Ron Weasley", "Gryffindor");
        List<StudentInfo> students = given()
                .params("name", "Hermione")
                .spec(spec)
                .when()
                .get(EndPoints.CONTEXT_PATH_SEARCH)
                .then()
                .spec(specSuccess)
                .body("student_name.flatten()", hasItem("Hermione Granger"))
                .extract().jsonPath().getList(".", StudentInfo.class);
        assertThat(students.size(), Matchers.greaterThan(0));
    }
}
