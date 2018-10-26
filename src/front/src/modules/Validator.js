class Validator {
    static validateEmail(email){
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    static validatePassword(pass){
        var rp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=\S+$)(?=.{8,})/;
        return rp.test(String(pass));
    }
}

export default Validator;
