package util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Text {

    public void welcome() {
        System.out.println("Вход в программу!");
    }

    public <T> String getTextLogError(Class<T> name) {
        return "Ошибка в " + name.getName() + " в методе "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " и  номер строки равен =" + Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

}
