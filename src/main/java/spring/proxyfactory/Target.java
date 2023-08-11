package spring.proxyfactory;

public class Target {
    public Result run() {
        return new Result();
    }

    public static class Result {
        private String code = "failed";

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "Result{" + "code='" + code + '\'' + '}';
        }
    }
}
