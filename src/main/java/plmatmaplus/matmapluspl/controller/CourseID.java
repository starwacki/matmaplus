package plmatmaplus.matmapluspl.controller;

public enum CourseID {

     BASE_MATH_ANALYSIS(1L),
     EXTENDED_MATH_ANALYSIS(2L),
     BASE_EXAM(3L),
     EXTENDED_EXAM(4L),
     PRIMARY_SCHOOL_EXAM(5L),
     INTEGRALS(6L);

     private  long courseId;

     private String courseRedirectedLink;

     CourseID(long courseId) {
         this.courseId = courseId;
     }

     public long getCourseId() {
         return courseId;
     }


 }
