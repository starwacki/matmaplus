package plmatmaplus.matmapluspl.controler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopProductsControllers {

    @RequestMapping("/matmaplus/shop/analizapodstawa")
    public String baseMathAnalysisPage() {
        return "analiza-matematyczna.html";
    }

    @RequestMapping("/matmaplus/shop/analizarozszerzona")
    public String extendedMathAnalysisPage() {
        return "analiza-matematyczna-roz.html";
    }

    @RequestMapping("/matmaplus/shop/maturapodstawowa")
    public String baseExamPage() {
        return "kurs-matura-podstawowa.html";
    }

    @RequestMapping("/matmaplus/shop/maturarozszerzona")
    public String extendedExamPage() {
        return "kurs-matura-rozszerzona.html";
    }


    @RequestMapping("/matmaplus/shop/egzamin-ósmioklasisty")
    public String primarySchoolExamPage() {
        return "egzamin-ósmioklasisty.html";
    }

    @RequestMapping("/matmaplus/shop/całki")
    public String integralsPage() {
        return "całki-na-studiach.html";
    }
}
