class NotificationData {
    open:boolean;
    message:string;
    
    constructor(open:boolean, message:string){
        this.open = open;
        this.message = message;
    }
}
export default NotificationData;