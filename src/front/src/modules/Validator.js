class Validator {
    static validateEmail(email){
        let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    static validatePassword(pass){
        let rp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=\S+$)(?=.{8,})/;
        return rp.test(String(pass));
    }

    static validName(name){
        let rx = /^([A-Z][a-z]+$)/;
        return rx.test(String(name));
    }

    static validImageURL(url){
        let rx = /(http(s?):)([/|.|\w|\s|-])*\.(?:jpg|gif|png)/;
        return rx.test(String(url));
    }
}

export default Validator;
