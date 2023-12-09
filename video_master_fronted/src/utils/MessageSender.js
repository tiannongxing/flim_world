import axios from "axios";

const getMessageSender = function(path, ...msgs) {
    // 传入path和msgs，返回一个JSON对象，JSON对象为后台返回的数据
    // path: 请求的路径
    // msgs: 请求的参数,格式为以queryTitle为key，queryBody为value的对象
    let generatedMsgs = ''
    if (msgs.length > 0) {
       for(let i =0;i<msgs.length;i++) {
           if(i>0) {
               generatedMsgs += '&'
           }
           generatedMsgs += `${msgs[i].queryTitle}=${msgs[i].queryBody}`
       }

    }

    console.log(`${path}?${generatedMsgs}`)
    // 返回一个promise对象,在调用的使用使用then或者await来获取数据
    return axios.get(`${path}?${generatedMsgs}`)
}

const postMessageSender = (path, ...msgs) => {

}

export { getMessageSender, postMessageSender }