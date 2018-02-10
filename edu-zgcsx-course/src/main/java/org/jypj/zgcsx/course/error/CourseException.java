package org.jypj.zgcsx.course.error;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 18:33
 */
public class CourseException extends RuntimeException {
    private Object[] args;
    static final long serialVersionUID = 7551091269596759360L;

    public CourseException(String message) {
        super(message);
    }

    public CourseException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public CourseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseException(Throwable cause) {
        super(cause);
    }

    public CourseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
