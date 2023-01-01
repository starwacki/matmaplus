package plmatmaplus.matmapluspl.controller;

enum Views {
    USER_COURSES_VIEW("usercourses.html"),
    ANALIZA_MATEMATYCZNA_PODST_VIEW("analiza-matematyczna.html"),
    ANALIZA_MATEMATYCZNA_ROZ_VIEW("analiza-matematyczna-roz.html"),
    KURS_MATURA_PODST_VIEW("kurs-matura-podstawowa.html"),
    KURS_MATURA_ROZ_VIEW("kurs-matura-rozszerzona.html"),
    EGZAMIN_ÓSMOKLASISTY_VIEW("egzamin-ósmioklasisty.html"),
    CAŁKI_NA_STUDIACH_VIEW("całki-na-studiach.html"),
    REGISTER_VIEW("register.html"),
    PAYMENT_VIEW("payment.html"),

    CART_VIEW("cart.html");

    private String view;

    Views(String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return view;
    }
}
