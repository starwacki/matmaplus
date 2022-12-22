package plmatmaplus.matmapluspl.dto;

public enum CourseIMGLinks {
    ANALIZA_MATEMATYCZNA_PODSTAWA("/resources/analiza-shop-pods.png"),
    ANALIZA_MATEMATYCZNA_ROZSZERZENIE("/resources/analiza-shop-roz.png"),
    KURS_MATURA_PODSTAWOWA("/resources/matura-podstaw.png"),
    KURS_MATURA_ROZSZERZONA("/resources/matura-roz.png"),
    EGZAMIN_OŚMOKLASISTY("/resources/egzamin-osmoklasisty.png"),
    CAŁKI_NA_STUDIACH("/resources/całki-na-studiach.png");

    private String link;

    CourseIMGLinks(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return link;
    }
}
