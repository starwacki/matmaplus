package plmatmaplus.matmapluspl.controller;

enum Views {

    CONTACT("contact.html"),
    BLOG("blog.html"),
    MAIN_VIEW("mainview.html"),
    SHOP_VIEW("shop.html"),
    LOGIN_VIEW("login.html"),
    USER_COURSES_VIEW("usercourses.html"),
    BASE_MATH_ANALYSIS_VIEW("analiza-matematyczna.html"),
    EXTENDED_MATH_ANALYSIS_VIEW("analiza-matematyczna-roz.html"),
    BASE_EXAM_VIEW("kurs-matura-podstawowa.html"),
    EXTENDED_EXAM_VIEW("kurs-matura-rozszerzona.html"),
    PRIMARY_SCHOOL_EXAM_VIEW("egzamin-ósmioklasisty.html"),
    INTEGRALS_VIEW("całki-na-studiach.html"),
    REGISTER_VIEW("register.html"),
    PAYMENT_VIEW("payment.html"),

    CART_VIEW("cart.html");

    private final String view;

    Views(String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return view;
    }
}
