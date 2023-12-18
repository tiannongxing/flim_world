class TokenMapHandler {
    static getToken() {
        return localStorage.getItem("token")
    }

    static addToken(token) {
        localStorage.setItem("token",token)
    }

    static parsingMap(map){
        let key = map["token"]
        TokenMapHandler.addToken(key)
        return map['data']
    }
}

export default TokenMapHandler