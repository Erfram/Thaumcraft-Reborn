package llama.thaumcraft.magic;

import java.util.Arrays;
import java.util.List;

public enum Aspect {
    AER("aer", 0xFFFF7E, null), //ВОЗДУХ
    TERRA("terra", 0x56C000, null), //ЗЕМЛЯ
    IGNIS("ignis", 0xFF5A01, null), //ЗЕМЛЯ
    AQUA("aqua", 0x3CD4FC, null), //ВОДА
    ORDO("ordo", 0xD5D4EC, null), //ПОРЯДОК
    PERDITIO("perditio", 0x404040, null), //ХАОС

    //--------

    VACOUS("vacous", 0x888888, Arrays.asList(AER, PERDITIO)), //ПУСТОТА
    LUX("lux", 0xFFFFC0, Arrays.asList(AER, IGNIS)), //СВЕТ
    MOTUS("motus", 0xCDCCF4, Arrays.asList(AER, ORDO)), //ДВИЖЕНИЕ
    GELUM("gelum", 0xE1FFFF, Arrays.asList(IGNIS, PERDITIO)), //ХОЛОД
    VITREUS("vitreus", 0x80FFD7, Arrays.asList(TERRA, AER)), //КРИСТАЛЛ/СТЕКЛО
    METALLUM("metallum", 0xFFFAFA, Arrays.asList(TERRA, ORDO)), //МЕТАЛЛ
    VICTUS("victus", 0x951214, Arrays.asList(TERRA, AQUA)), //ЖИЗНЬ
    MORTUS("mortus", 0x960000, Arrays.asList(VICTUS, PERDITIO)), //СМЕРТЬ
    POTENTIA("potentia", 0x8CD5CD, Arrays.asList(ORDO, IGNIS)), //СИЛА
    PERMUTATIO("permutatio", 0xDC69DE, Arrays.asList(PERDITIO, ORDO)), //КРУГОВОРОТ
    PRAECANTATIO("praecantatio", 0xD2B30F, Arrays.asList(POTENTIA, AER)), //МАГИЯ
    AURAM("auram", 0x66216E, Arrays.asList(PRAECANTATIO, AER)), //АУРА
    ALKIMIA("alkimia", 0x1E363F, Arrays.asList(PRAECANTATIO, AQUA)), //АЛХИМИЯ
    VITIUM("vitium", 0x66216E, Arrays.asList(PERDITIO, PRAECANTATIO)), //ИНФЕКЦИЯ
    TENEBRAE("tenebrae", 0x1E1C22, Arrays.asList(VACOUS, LUX)), //ТЬМА
    ALIENIS("alienis", 0x523B71, Arrays.asList(TENEBRAE, VACOUS)), //ЧУЖОЙ
    VOLATUS("volatus", 0xF8F4FF, Arrays.asList(AER, MOTUS)), //ПОЛЁТ
    HERBA("herba", 0x1B911B, Arrays.asList(VICTUS, TERRA)), //ЗАРОЖДЕНИЕ
    INSTRUMENTUM("instrumentum", 0x1F32AA, Arrays.asList(METALLUM, POTENTIA)), //ИНСТРУМЕНТ
    FABRICO("fabrico", 0x589263, Arrays.asList(PERMUTATIO, INSTRUMENTUM)), //СОЗДАНИЕ
    MACHINA("machina", 0x465356, Arrays.asList(MOTUS, INSTRUMENTUM)), //МЕХАНИЗМ
    VINCULUM("vinculum", 0x968A6F, Arrays.asList(MOTUS, PERDITIO)), //ЛОВУШКА
    SPIRITUS("spiritus", 0x434b4f, Arrays.asList(VICTUS, MORTUS)), //ДУША
    COGNITIO("cognitio", 0xFFCB8F, Arrays.asList(IGNIS, SPIRITUS)), //ПОЗНАНИЕ
    SENSUS("sensus", 0xC4fDC3, Arrays.asList(AER, SPIRITUS)), //ОСЯЗАНИЕ
    AVERSIO("aversio", 0x00BFBD, Arrays.asList(SPIRITUS, PERDITIO)), //ВРЕД
    PRAEMUNIO("praemunio", 0x77DDEE, Arrays.asList(SPIRITUS, TERRA)), //ЗАЩИТА
    DESIDERIUM("desiderium", 0xE1BD47, Arrays.asList(SPIRITUS, VACOUS)), //АЛЧНОСТЬ
    EXANIMIS("exanimis", 0x3a4000, Arrays.asList(MOTUS, MORTUS)), //НЕЖИТЬ
    BESTIA("bestia", 0xA0620E, Arrays.asList(MOTUS, VICTUS)), //ЖИВОТНОЕ/ЖИВОЕ
    HUMANUS("humanus", 0xFDD8C4, Arrays.asList(SPIRITUS, VICTUS)), //ЧЕЛОВЕК
    ECHO("echo", 0x052730, Arrays.asList(VACOUS, ALIENIS)), //ЭХО
    INFERNO("inferno", 0x880808, Arrays.asList(IGNIS, POTENTIA)), //Ад/Пламя
    TEMPUS("tempus", 0xDAF7A6, Arrays.asList(SENSUS, MACHINA)); //Время МБ НАДО ПЕРМУТАТИО

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

    public static Aspect findByColor(int color) {
        for(Aspect aspect : Aspect.values()) {
            if(aspect.getColor() == color) {
                return aspect;
            }
        }

        return null;
    }

    public List<Aspect> getIngredients() {
        return this.ingredients;
    };
}
