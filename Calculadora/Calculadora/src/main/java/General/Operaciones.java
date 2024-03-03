package General;

public class Operaciones {

    public static boolean esIgualA0(String text){
        return text.equals("0");
    }

    public static String operar(String num1, String num2, String operacion) {
        double resultado = 0;
        double num1ToDouble = Double.parseDouble(num1);
        double num2ToDouble = Double.parseDouble(num2);

        switch (operacion) {
            case "+":
                resultado = num1ToDouble + num2ToDouble;
                break;
            case "-":
                resultado = num1ToDouble - num2ToDouble;
                break;
            case "*":
                resultado = num1ToDouble * num2ToDouble;
                break;
            case "/":
                resultado = num1ToDouble / num2ToDouble;
                break;
            case "^":
                resultado = Math.pow(num1ToDouble,num2ToDouble);
                break;
        }
        String resultadoString = String.valueOf(resultado);
        if (resultadoString.endsWith(".0")) {
            return resultadoString.substring(0, resultadoString.length() - 2);
        } else {
            return resultadoString;
        }
    }

    public static String operarTrigonometria(String num, String operacion) {
       String resultado = "";
       double numeroInicial = Math.toRadians(Double.parseDouble(num));
        switch (operacion) {
            case "SIN":
                resultado =  String.valueOf(Math.sin(numeroInicial));
                break;
            case "COS":
                resultado = String.valueOf(Math.cos(numeroInicial));
                break;
            case "TAN":
                resultado = String.valueOf(Math.tan(numeroInicial));
                break;
        }
        if (resultado.endsWith(".0")){
            return resultado.substring(0, resultado.length()-2);
        }else{
            return resultado;
        }
    }
}
