import axios from "axios";

const getMessageSender = function(path, ...msgs) {
    // 传入path和msgs，返回一个JSON对象，JSON对象为后台返回的数据
    // path: 请求的路径
    // msgs: 请求的参数,格式为以queryTitle为key，queryBody为value的对象
    // 返回一个promise对象,在调用的使用使用then或者await来获取数据
    let params = generateParams(msgs)
    return axios.get(`${path}?${params}`)
}

const postMessageSender = (path, ...msgs) => {
    console.log(msgs)
    let params = {}
    if(msgs.length > 0){
        msgs.forEach(item => {
            params[item.queryTitle] = item.queryBody
        })
    }

    console.log(params)
    return msgs.length>0?axios.post(path,params):axios.post(path)
}

const generateParams = (messages) => {
    let generatedMsgs = ''
    if (messages.length > 0) {
        for(let i =0;i<messages.length;i++) {
            if(i>0) {
                generatedMsgs += '&'
            }
            generatedMsgs += `${messages[i].queryTitle}=${messages[i].queryBody}`
        }
    }
    return generatedMsgs
}

export { getMessageSender, postMessageSender }