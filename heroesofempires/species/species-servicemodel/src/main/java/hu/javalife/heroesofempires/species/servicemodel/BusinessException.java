package hu.javalife.heroesofempires.species.servicemodel;

/**
 * @author krisztian
 */
public class BusinessException extends Exception{
    private int code;

    public BusinessException() {
    }

    public BusinessException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
    
}
