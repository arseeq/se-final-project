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
        let rx = /(http(s?):)([/|.|\w|\s|-])*\.(?:jpg|gif|png|jpeg)/;
        return rx.test(String(url)) || url.length === 0 ;
    }

    static validEventName(eventName){
        return eventName.length > 0;
    }

    static validEventLocation(eventLocation){
        return eventLocation.length > 0;
    }

    static validEventDate(eventDate){
        return eventDate.length > 0;
    }

    static validEventTime(eventTime){
        return eventTime.length > 0;
    }

    static validEventPrice(eventPrice){
        return (eventPrice.length > 0 && eventPrice >= 0);
    }

    static validEventPoints(eventPoints){
        return (eventPoints.length > 0 && eventPoints >= 0);
    }

    static validEventCategory(eventCategory){
        return eventCategory.length > 0;
    }

    static validEventParticipants(eventParticipants){
        return (eventParticipants.length > 0 && eventParticipants >= 2);
    }
}

export default Validator;