package llama.thaumcraft;

import java.util.Arrays;
import java.util.List;

public enum Aspects {
    AER("aer", 16777086, null), //ВОЗДУХ
    TERRA("terra", 5685248, null), //ЗЕМЛЯ
    AQUA("aqua", 3986684, null), //ВОДА
    IGNIS("ignis", 16734721, null), //ЗЕМЛЯ
    ORDO("ordo", 14013676, null), //ПОРЯДОК
    PERDITIO("perditio", 4210752, null), //ХАОС

    //--------

    GELUM("gelum", 14811135, Arrays.asList(IGNIS, PERDITIO)), //ХОЛОД
    LUX("lux", 16777152, Arrays.asList(AER, IGNIS)), //СВЕТ
    MOTUS("motus", 13487348, Arrays.asList(AER, ORDO)), //ДВИЖЕНИЕ
    PERMUTATIO("permutatio", 5735255, Arrays.asList(ORDO, PERDITIO)), //КРУГОВОРОТ
    POTENTIA("potentia", 12648447, Arrays.asList(IGNIS, ORDO)), //СИЛА
    TEMPESTAS("tempestas", 16775920, Arrays.asList(AER, AQUA)), //ПОГОДА
    VACOUS("vacous", 8947848, Arrays.asList(AER, PERDITIO)), //ПУСТОТА
    VENENUM("venenum", 8190976, Arrays.asList(AQUA, PERDITIO)), //ЯД
    VICTUS("victus", 14548997, Arrays.asList(AQUA, TERRA)), //ЖИЗНЬ
    VITREUS("vitreus", 8454143, Arrays.asList(ORDO, TERRA)), //КРИСТАЛЛ/СТЕКЛО

    //--------

    BESTIA("bestia", 10445833, Arrays.asList(MOTUS, VICTUS)), //ЖИВОТНОЕ/ЖИВОЕ
    FAMES("fames", 9109504, Arrays.asList(VACOUS, VICTUS)), //ГОЛОД
    HERBA("herba", 109568, Arrays.asList(TERRA, VICTUS)), //ЗАРОЖДЕНИЕ
    ITER("iter", 15422084, Arrays.asList(MOTUS, TERRA)), //СТРАНСТВИЕ
    LIMUS("limus", 3459364, Arrays.asList(AQUA, VICTUS)), //ВЯЗКОСТЬ/СЛИЗЬ
    METALLUM("metallum", 11908557, Arrays.asList(TERRA, VITREUS)), //МЕТАЛЛ
    MORTUS("mortus", 6946821, Arrays.asList(PERDITIO, VICTUS)), //СМЕРТЬ
    PRAECANTATIO("praecantatio", 13566207, Arrays.asList(POTENTIA, VACOUS)), //МАГИЯ
    SANO("sano", 9568266, Arrays.asList(ORDO, VICTUS)), //ЛЕЧЕНИЕ
    TENEBRAE("tenebrae", 2236962, Arrays.asList(LUX, VACOUS)), //ТЬМА
    VINCULUM("vinculum", 10125440, Arrays.asList(MOTUS, PERDITIO)), //ЛОВУШКА
    VOLATUS("volatus", 15198167, Arrays.asList(AER, MOTUS)), //ПОЛЁТ

    //--------

    ALIENIS("alienis", 8409216, Arrays.asList(TENEBRAE, VACOUS)), //ЧУЖОЙ
    ARBOR("arbor", 7553570, Arrays.asList(AER, HERBA)), //ДРЕВЕСИНА
    AURAM("auram", 16761087, Arrays.asList(AER, PRAECANTATIO)), //АУРА
    CORPUS("corpus", 16525383, Arrays.asList(AER, PRAECANTATIO)), //ПЛОТЬ
    EXANIMIS("exanimis", 3817472, Arrays.asList(MORTUS, MOTUS)), //БЕССИЛЬНЫЙ
    SPIRITUS("spiritus", 15461371 , Arrays.asList(MORTUS, VICTUS)), //ДУША
    VITIUM("vitium", 8388736, Arrays.asList(PERDITIO, PRAECANTATIO)), //ИНФЕКЦИЯ

    //--------

    COGNITIO("cognitio", 16356991, Arrays.asList(IGNIS, SPIRITUS)), //ПОЗНАНИЕ
    SENSUS("sensus", 12648384, Arrays.asList(AER, SPIRITUS)), //ОСЯЗАНИЕ

    //--------

    HUMANUS("humanus", 16766912, Arrays.asList(BESTIA, COGNITIO)), //ЧЕЛОВЕК

    //--------

    INSTRUMENTUM("instrumentum", 4210926, Arrays.asList(HUMANUS, ORDO)), //ИНСТРУМЕНТ
    LUCRUM("lucrum", 14139945, Arrays.asList(FAMES, HUMANUS)), //АЛЧНОСТЬ
    MESSIS("messis", 12687979, Arrays.asList(HERBA, HUMANUS)), //УРОЖАЙ
    PERFODIO("perfodio", 14474460, Arrays.asList(HUMANUS, TERRA)), //ДОБЫЧА


    //--------

    FABRICO("fabrico", 8428928, Arrays.asList(HUMANUS, INSTRUMENTUM)), //СОЗДАНИЕ
    MACHINA("machina", 8421536, Arrays.asList(INSTRUMENTUM, MOTUS)), //МЕХАНИЗМ
    METO("meto", 11220026, Arrays.asList(INSTRUMENTUM, MESSIS)), //СБОР/ЖАТВА
    PANNUS("pannus", 16769719, Arrays.asList(BESTIA, INSTRUMENTUM)), //ТКАНЬ/МАТЕРИАЛ
    TELUM("telum", 13508649, Arrays.asList(INSTRUMENTUM, IGNIS)), //ВРЕД
    TUTAMEN("tutamen", 7855591, Arrays.asList(INSTRUMENTUM, TERRA)); //ЗАЩИТА

    private String id;
    private int color;
    private List<Aspects> ingredients;

    Aspects(String id, int color, List<Aspects> ingredients) {
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

    public static Aspects findByColor(int color) {
        for(Aspects aspect : Aspects.values()) {
            if(aspect.getColor() == color) {
                return aspect;
            }
        }

        return null;
    }

    public List<Aspects> getIngredients() {
        return this.ingredients;
    };
}
