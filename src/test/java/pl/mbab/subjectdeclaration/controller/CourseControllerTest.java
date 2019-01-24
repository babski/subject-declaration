//package pl.mbab.subjectdeclaration.controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import pl.mbab.subjectdeclaration.model.user.User;
//import pl.mbab.subjectdeclaration.service.CourseService;
//import pl.mbab.subjectdeclaration.service.UserService;
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//class CourseControllerTest {
//
//    @Mock
//    CourseService courseService;
//
//    @Mock
//    UserService userService;
//
//    @InjectMocks
//    CourseController courseController;
//
//    MockMvc mockMvc;
//
//    User user;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(courseController)
//                .build();
//        user = new User("kuba@wp.pl");
//
//    }
//
//    @Test
//    void listAllCourses() throws Exception {
//        mockMvc.perform(get("/courses/all"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("courses"))
//                .andExpect(model().attributeExists("courses", "user"));
//
//    }
//
//    @Test
//    void listFieldCoursesA() {
//    }
//
//    @Test
//    void listFieldCoursesB() {
//    }
//
//    @Test
//    void addCourseById() {
//    }
//
//    @Test
//    void deleteCourseById() {
//    }
//}