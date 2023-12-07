class MessageObj{
    constructor(queryTitle,queryBody) {
        this.mesg = {
            queryTitle : queryTitle,
            queryBody : queryBody
        }
    }

    getObject(){
        return this.mesg
    }
}

export default MessageObj