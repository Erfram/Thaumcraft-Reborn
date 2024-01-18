package llama.thaumcraft;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public enum Aspect {
    AER("aer", 16764736, null), //ВОЗДУХ
    TERRA("terra", 43776, null), //ЗЕМЛЯ
    AQUA("aqua", 4369151, null), //ВОДА
    IGNIS("ignis", 16711680, null), //ЗЕМЛЯ
    ORDO("ordo", 16119000, null), //ПОРЯДОК
    PERDITIO("perditio", 2699571, null), //ХАОС

    //--------

    GELUM("gelum", 0, Arrays.asList(IGNIS, PERDITIO)), //ХОЛОД
    LUX("lux", 0, Arrays.asList(AER, IGNIS)), //СВЕТ
    MOTUS("motus", 0, Arrays.asList(AER, ORDO)), //ДВИЖЕНИЕ
    PERMUTATIO("permutatio", 0, Arrays.asList(ORDO, PERDITIO)), //КРУГОВОРОТ
    POTENTIA("potentia", 0, Arrays.asList(IGNIS, ORDO)), //СИЛА
    TEMPESTAS("tempestas", 0, Arrays.asList(AER, AQUA)), //ПОГОДА
    VACOUS("vacous", 0, Arrays.asList(AER, PERDITIO)), //ПУСТОТА
    VENENUM("venenum", 0, Arrays.asList(AQUA, PERDITIO)), //ЯД
    VICTUS("victus", 0, Arrays.asList(AQUA, TERRA)), //ЖИЗНЬ
    VITREUS("vitreus", 0, Arrays.asList(ORDO, TERRA)), //КРИСТАЛЛ/СТЕКЛО

    //--------

    BESTIA("bestia", 0, Arrays.asList(MOTUS, VICTUS)), //ЖИВОТНОЕ/ЖИВОЕ
    FAMES("fames", 0, Arrays.asList(VACOUS, VICTUS)), //ГОЛОД
    HERBA("herba", 0, Arrays.asList(TERRA, VICTUS)), //ЗАРОЖДЕНИЕ
    ITER("iter", 0, Arrays.asList(MOTUS, TERRA)), //СТРАНСТВИЕ
    LIMUS("limus", 0, Arrays.asList(AQUA, VICTUS)), //ВЯЗКОСТЬ/СЛИЗЬ
    METALLUM("metallum", 0, Arrays.asList(TERRA, VITREUS)), //МЕТАЛЛ
    MORTUS("mortus", 0, Arrays.asList(PERDITIO, VICTUS)), //МЕТАЛЛ
    PRAECANTIO("praecantatio", 0, Arrays.asList(POTENTIA, VACOUS)), //МАГИЯ
    SANO("sano", 0, Arrays.asList(ORDO, VICTUS)), //ЛЕЧЕНИЕ
    TENEBRAE("tenebrae", 0, Arrays.asList(LUX, VACOUS)), //ТЬМА
    VINCULUM("vinculum", 0, Arrays.asList(MOTUS, PERDITIO)), //ЛОВУШКА
    VOLATUS("volatus", 0, Arrays.asList(AER, MOTUS)), //ПОЛЁТ

    //--------

    ALIENIS("alienis", 0, Arrays.asList(TENEBRAE, VACOUS)), //ЧУЖОЙ
    ARBOR("arbor", 0, Arrays.asList(AER, HERBA)), //ДРЕВЕСИНА
    AURAM("auram", 0, Arrays.asList(AER, PRAECANTIO)), //АУРА
    CORPUS("corpus", 0, Arrays.asList(AER, PRAECANTIO)), //ПЛОТЬ
    EXANIMIS("exanimis", 0, Arrays.asList(MORTUS, MOTUS)), //ПЛОТЬ
    SPIRITUS("spiritus", 0, Arrays.asList(MORTUS, VICTUS)), //ДУША
    VITIUM("vitium", 0, Arrays.asList(PERDITIO, PRAECANTIO)), //ИНФЕКЦИЯ

    //--------

    COGNITIO("cognitio", 0, Arrays.asList(IGNIS, SPIRITUS)), //ПОЗНАНИЕ
    SENSUS("sensus", 0, Arrays.asList(AER, SPIRITUS)), //ОСЯЗАНИЕ

    //--------

    HUMANUS("humanus", 0, Arrays.asList(BESTIA, COGNITIO)), //ЧЕЛОВЕК

    //--------

    INSTRUMENTUM("instrumentum", 0, Arrays.asList(HUMANUS, ORDO)), //ИНСТРУМЕНТ
    LUCRUM("lucrum", 0, Arrays.asList(FAMES, HUMANUS)), //АЛЧНОСТЬ
    MESSIS("messis", 0, Arrays.asList(HERBA, HUMANUS)), //УРОЖАЙ
    PERFODIO("perfodio", 0, Arrays.asList(HUMANUS, TERRA)), //ДОБЫЧА

    //--------

    FABRICO("fabrico", 0, Arrays.asList(HUMANUS, INSTRUMENTUM)), //СОЗДАНИЕ
    MACHINA("machina", 0, Arrays.asList(INSTRUMENTUM, MOTUS)), //МЕХАНИЗМ
    METO("meto", 0, Arrays.asList(INSTRUMENTUM, MESSIS)), //СБОР/ЖАТВА
    PANNUS("pannus", 0, Arrays.asList(BESTIA, INSTRUMENTUM)), //ТКАНЬ/МАТЕРИАЛ
    TELUM("telum", 0, Arrays.asList(INSTRUMENTUM, IGNIS)), //ВРЕД
    TUTAMEN("tutamen", 0, Arrays.asList(INSTRUMENTUM, TERRA)); //ЗАЩИТА

    private String id;
    private int color;
    private List<Aspect> ingredients;

    Aspect(String id, int color, List<Aspect> ingredients) {
        this.id = id;
        this.color = color;
        this.ingredients = ingredients;
    }

    public String getName() {
        return this.id;
    }

    public int getColor() {
        return this.color;
    }

    public List<Aspect> getIngredients() {
        return this.ingredients;
    };
}
