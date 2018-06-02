# Prerequisites and project setup

Project has been developed using Spring Boot as a Java Framework, Maven as build tool, Travis CI as Continous Integration, Swagger UI as rest documentation and specs,
MySQL as RDBMS. Make sure that port 8080 is not binded when you will test the application. You can test the REST functionallity via swagger UI.

```
http://localhost:8080/swagger-ui.html
```

REST API credentials ( used basic in memory authentication )

```
user=stefania
password=stefania
```
Create MySQL DB - univmgmt_db and be sure you can connect with root/root on it. All the tables will be created by Java Persistence API.


This is application properties content for connecting to MySQL database:

```
spring.datasource.url=jdbc:mysql://localhost:3306/univmgmt_db?useSSL=false
spring.datasource.username=root
spring.datasource.password=root
```

Install project

```
git clone https://github.com/stephenie11/StudentManagement.git

cd cd StudentManagement/

mvn clean install

mvn spring-boot:run

```

Go to ```http://localhost:8080/swagger-ui.html``` and type user stefania and password stefania in order to have access to the API and test the API.

### Swagger UI API documentation routes

![swagger-ui](img/swagger-ui.png)

### API Operations

The way I think is that you create courses and students and then you assign courses to students or add students to a course.

```
POST /api/course
```

Set request courseTitle to course title. Let's choose for example Formal Languages and Automata.
Create another course with courseTitle Object Oriented Programming.

```
GET /api/courses/view
```

This will show you all the courses stored in the database. You will see that courses Formal Languages and Automata and 
Object Oriented Programming have been created.

```
GET /api/courses/list
```

This will list you courses by pages. In order to test, you can set page with 0 / 1 and size with 1.

Now let's add some students and then we will come back to the remaining two operations on courses ( patch and delete )

```
POST /api/students
```

Create two students ( two requests ). 

First request:

```
{
  "cnp": "2970121035286",
  "college_year": 3,
  "courses": [],
  "email": "pirvu.stefania@gmail.com",
  "first_name": "Stefania",
  "last_name": "Pirvu",
  "phone_number": "0739042106",
  "registration_number": "144/24",
  "student_id": null
}
```

Second request:

```
{
  "cnp": "2970321035287",
  "college_year": 1,
  "courses": [],
  "email": "pirvu.alexandra@gmail.com",
  "first_name": "Alexandra",
  "last_name": "Pirvu",
  "phone_number": "0739042107",
  "registration_number": "144/25",
  "student_id": null
}
```


In order to view all the students or list students by pages:

```
GET /api/students/view
GET /api/students/list
```

Now let's add a course to a student:

```
PATCH /api/students/{id}    
```

Set id to 2 for your student stored in database.
Set body with this:

```
{
  "cnp": "2970321035287",
  "college_year": 1,
  "courses": [{
     "course_id": 1,
     "course_title": "Formal Languages and Automata"
   },{
     "course_id": 2,
     "course_title": "Object Oriented Programming"
   }
   ],
  "email": "pirvu.alexandra@gmail.com",
  "first_name": "Alexandra",
  "last_name": "Pirvu",
  "phone_number": "0739042107",
  "registration_number": "144/25",
  "student_id": 2
}
```


```
PUT /api/students/{id}    
```

It is used to replace student, patch will update only fields that can be updated, not fields like cnp or registration_number.


In order to delete a student.

```
DELETE /api/students/{id}    
```

Set path variable studentId to 2. It will delete student Alexandra.

```
PATCH /api/courses/{id}    
```

```
{
        "course_id": 1,
        "course_title": "Formal Languages and Automata",
        "students": [
            {
              "cnp": "2970121035286",
              "college_year": 3,
              "courses": [],
              "email": "pirvu.stefania@gmail.com",
              "first_name": "Stefania",
              "last_name": "Pirvu",
              "phone_number": "0739042106",
              "registration_number": "144/24",
              "student_id": null
            }
        ]
       }
```


```
    DELETE /api/courses
```
Set courseId request param with 1 and delete Formal Languages Automata course.

### Continous Integration

Travis CI URL 

```
    https://travis-ci.org/stephenie11/StudentManagement
```

![continous-integration](img/continous-integration.png)

### Tests 

Implemented 10 tests

![tests](img/tests.png)