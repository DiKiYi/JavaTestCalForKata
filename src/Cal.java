import java.util.Scanner;



public class Cal {
    static int num1;                    //использовался в первой, закомментированной версии кода
    static int m = 0;                   //наличие минуса
    static int i = 0;                   //количество римских цифр
    static Character min = '-';         //сверка char
    static int n = 0;                   //использовался в первой, закомментированной версии кода
    static int whereMinus;
    static int wherePlus;
    static int whereDel;
    static int whereMnog;
    static int whereOp;
    static String enterOperation;
    static String enterOperationUnneg;
    static String aftOpUnneg;
    static String befOpUnneg;
    static String oper;
    static int p = 0;               // количество проверок

    public static void main(String[] args) throws ContErr{
        toBegin();

        //ниже, на 40 строк, первая версия кода, в целом работала, но с несколькими минусами была целая проблема.
//        while (true) {
//            i = 0;
//            System.out.println("Ведите операцию сложения, вычитания, умножения или деления с двумя числами: a + b, a - b, a * b, a / b, где a и b - целые числа от 1 до 10 написанные только арабскими или только римскими цифрами.");
//            String enterOperation = new Scanner(System.in).nextLine();
//            boolean hasPlus  = (enterOperation.indexOf(43) != -1);
//            boolean hasMinus = (enterOperation.indexOf(45) != -1);
//            boolean hasDel   = (enterOperation.indexOf(47) != -1);
//            boolean hasMnog  = (enterOperation.indexOf(42) != -1);
//            if (hasPlus && !hasMinus && !hasMnog && !hasDel) {
//                String[] numbers = enterOperation.split("\\+");
//                if (numbers.length == 2) {
//                    math(numbers[0], numbers[1], "plus");
//                } else {
//                    brLotO();
//                }
//            } else if (!hasPlus && hasMinus && !hasMnog && !hasDel) {
//                String[] numbers = enterOperation.split("\\-");
//                if (numbers.length == 2) {
//                    math(numbers[0], numbers[1], "minus");
//                } else {
//                    brLotO();
//                }
//            } else if (!hasPlus && !hasMinus && hasMnog && !hasDel) {
//                String[] numbers = enterOperation.split("\\*");
//                if (numbers.length == 2) {
//                    math(numbers[0], numbers[1], "mnog");
//                } else {
//                    brLotO();
//                }
//            } else if (!hasPlus && !hasMinus && !hasMnog && hasDel) {
//                String[] numbers = enterOperation.split("\\/");
//                if (numbers.length == 2) {
//                    math(numbers[0], numbers[1], "del");
//                } else{
//                    System.out.println("throws Exception //т.к. строка не является математической операцией");
//                }
//            } else {
//                brLotO();
//            }
//        }
//        int num1 = Integer.parseInt(enter1); // погуглил
    }

    static void toBegin() throws ContErr {
        while (true) {
            System.out.println("Ведите операцию сложения, вычитания, умножения или деления с двумя числами: a + b, a - b, a * b, a / b, " +
                    "где a и b - целые числа от -10 до 10, написанные арабскими цифрами или от I до X, написанные римскими цифрами.");
            i = 0;
            m = 0;
            p = 0;
            enterOperation = new Scanner(System.in).nextLine();
            if (enterOperation.equals("")) {
                noMath();
            }
            if (min.equals(enterOperation.charAt(0))) {   //добавляет переменную если нашла минус в начале строки
                m++;
            }
            enterOperationUnneg = enterOperation.substring(m);  //добавляет отредактировынный ввод, без минуса в начале при его наличии
//            System.out.println("копия без минуса " + enterOperationUnneg);
            whereMinus = whereOpCheck(45);
            whereDel = whereOpCheck(47);
            whereMnog = whereOpCheck(42);
            wherePlus = whereOpCheck(43);
            whereOp = Math.min(Math.min(whereMinus, whereDel), Math.min(whereMnog, wherePlus)); //находит следующий после первого числа символ
            if (whereOp == whereMinus) {                //определяет символ математической операции из предложенных заданием
                oper = "minus";
            } else if (whereOp == whereDel) {
                oper = "del";
            } else if (whereOp == wherePlus) {
                oper = "plus";
            } else {
                oper = "mnog";
            }
//            System.out.println("положение знака " + whereOp);
            if (whereOp > 100) {                                              //проверяет наличие предложенных заданием символов (что больше 100 - фиктивные из метода whereOpCheck
                noMath();
            }
            String befOp = enterOperation.substring(0, (whereOp + m));    //отделяет от ввода строку до первого мат. символа, не учитывая минус в начале строки
            String aftOp = enterOperation.substring((whereOp + 1 + m));     //отделяет от ввода строку после первого мат. символа, не учитывая минус в начале строки
            m = 0;                                                        //обнуление наличия минуса для тестирование второй половины ввода
            if (min.equals(aftOp.charAt(0))) {                      //проверка минуса в началя второго "числа"
                m++;
            }
            aftOpUnneg = aftOp.substring(m);                        //убирает минуc в начале при наличии
            if (hasOp(aftOpUnneg)) {                                //проверяет мат.символы в строке
                brLotO();
            }
            m = 0;
            if (min.equals(befOp.charAt(0))) {                      //проверка минуса в началя первого "числа"
                m++;
            }
            befOpUnneg = befOp.substring(m);                        //обрезает минус при наличии в первом "числе"
            befOpUnneg = getInArab(befOpUnneg);                   //проверяет на соответствие римским цифрам
            aftOpUnneg = getInArab(aftOpUnneg);
            if (i == 1) {                            //из двух чисел лишь одно - римское
                arAndRum();
            }
            if (i > 0 && m > 0) {       //проверка на отрицательность римского числа
                negPum();
            }
            math(befOp, aftOp, oper);
        }
    }

    static void math(String str1, String str2, String oper) throws ContErr {
//NumberFormatException
        i = 0;
        str1 = getInArab(str1);
        str2 = getInArab(str2);
        try {
            int num1 = Integer.parseInt(str1);
            int num2 = Integer.parseInt(str2);
            if (!(num1 > -11 && num1 < 11) || !(num2 > -11 && num2 < 11)) {
                moreDiap();
            }
            switch (oper) {
                case "plus":
                    if (i == 2) {                                                 // если числа при вводе были римские - шлёт в римский метод,
                        ansInPum(num1 + num2);
                    } else {                                                      //если арабские - выводит ответ
                        System.out.println(num1 + num2);
                    }
                    break;
                case "minus":
                    if (i == 2) {
                        ansInPum(num1 - num2);
                    } else {
                        System.out.println(num1 - num2);
                    }
                    break;
                case "del":
                    if (num2 == 0) {
                        delZero();
                    }
                    if (i == 2) {
                        ansInPum(num1 / num2);
                    } else {
                        System.out.println(num1 / num2);
                    }
                    break;
                case "mnog":
                    if (i == 2) {
                        ansInPum(num1 * num2);
                    } else {
                        System.out.println(num1 * num2);
                    }
                    break;
            }
        } catch (NumberFormatException err) {
            unnownNum();
        }
    }

    static String getInArab(String pums) throws ContErr{
        switch (pums) {                                  //один большой костыль. преобразует строку римскую в строку арабскую
            case "I":
                pums = "1";
                i++;
                return pums;
            case "II":
                pums = "2";
                i++;
                return pums;
            case "III":
                pums = "3";
                i++;
                return pums;
            case "IV":
                pums = "4";
                i++;
                return pums;
            case "V":
                pums = "5";
                i++;
                return pums;
            case "VI":
                pums = "6";
                i++;
                return pums;
            case "VII":
                pums = "7";
                i++;
                return pums;
            case "VIII":
                pums = "8";
                i++;
                return pums;
            case "IX":
                pums = "9";
                i++;
                return pums;
            case "X":
                pums = "10";
                i++;
                return pums;
            default:
                return pums;

        }
    }

    static void ansInPum(int ans) throws ContErr {
        if (ans < 1) {                           //проверяет ответ римских на значение меньше единицы
            negPum();
        }
        if (ans==100){
            System.out.println("C");
            toBegin();
        }
        int edin = ans % 10;
        String ans1 = getEdinInPum(edin);
        int ans10 = (ans-edin)/10;
        switch (ans10){
            case 0:
                System.out.println(ans1);
                break;
            case 1:
                System.out.println("X"+ans1);
                break;
            case 2:
                System.out.println("XX"+ans1);
                break;
            case 3:
                System.out.println("XXX"+ans1);
                break;
            case 4:
                System.out.println("XL"+ans1);
                break;
            case 5:
                System.out.println("L"+ans1);
                break;
            case 6:
                System.out.println("LX"+ans1);
                break;
            case 7:
                System.out.println("LXX"+ans1);
                break;
            case 8:
                System.out.println("LXXX"+ans1);
                break;
            case 9:
                System.out.println("XC"+ans1);
                break;
        }
    }

        static String getEdinInPum(int ed) {
            switch (ed) {                             //ещё один большой костыль.
                case 1:
                    return "I";
                case 2:
                    return "II";
                case 3:
                    return "III";
                case 4:
                    return "IV";
                case 5:
                    return "V";
                case 6:
                    return "VI";
                case 7:
                    return "VII";
                case 8:
                    return "VIII";
                case 9:
                    return "IX";
            }
            return "";
        }

    static int whereOpCheck(int indexCheck) throws ContErr{                          //проверяет местоположение символа по индексу на отредактированном вводе
        int check = enterOperationUnneg.indexOf(indexCheck);
        if (check == 0) {
            brLotO();
        } else if (check < 0) {
            p++;                           //принудительно указывает индекс отсутствующих символов (-1) как 100+p для будущего сравнения, так как потребуется вычислить
            check = 100 + p;               //следующий математический символ после числа
        }
        return check;
    }

    static boolean hasOp(String checkSt) throws ContErr{                                   //проверяет наличие мат. символов
        boolean hasPlus = (checkSt.indexOf(43) != -1);
        boolean hasMinus = (checkSt.indexOf(45) != -1);
        boolean hasDel = (checkSt.indexOf(47) != -1);
        boolean hasMnog = (checkSt.indexOf(42) != -1);
        boolean hasOp = hasDel || hasMnog || hasMinus || hasPlus;
        return hasOp;
    }

    static void negPum() throws ContErr{
        throw new ContErr("//т.к. в римской системе нет отрицательных чисел");
//        System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
//        toBegin();

    }

    static void brLotO() throws ContErr{
        throw new ContErr("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
//        System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
//        toBegin();
    }

    static void noMath() throws ContErr{
        throw new ContErr("//т.к. строка не является математической операцией");
//        System.out.println("throws Exception //т.к. строка не является математической операцией");
//        toBegin();
    }

    static void arAndRum() throws ContErr{
        throw new ContErr("//т.к. используются одновременно разные системы счисления");
//        System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
//        toBegin();
    }

    static void moreDiap() throws ContErr{
        throw new ContErr("//т.к. число не соответстует требованиям от -10 до 10");
//        System.out.println("throws Exception //т.к. число не соответстует требованиям от -10 до 10");
//        toBegin();
    }

    static void delZero() throws ContErr{
        throw new ContErr("//т.к.нельзя делить на 0");
//        System.out.println("throws Exception //т.к.нельзя делить на 0");
//        toBegin();
    }

    static void unnownNum() throws ContErr{
        throw new ContErr("//т.к. как минимум одно неизвестное число");
//        System.out.println("throws Exception //т.к. как минимум одно неизвестное число");
//        toBegin();
        }
    }