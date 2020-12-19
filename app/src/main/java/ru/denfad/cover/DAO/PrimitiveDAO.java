package ru.denfad.cover.DAO;

import ru.denfad.cover.models.Person;

public class PrimitiveDAO {
    private static PrimitiveDAO instance;
    public double x, y;
    public Person person;

    private PrimitiveDAO() {
    }

    public static PrimitiveDAO getInstance() {
        if (instance == null) {
            instance = new PrimitiveDAO();
        }
        return instance;
    }
}
