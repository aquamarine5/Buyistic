export default {
    GetQueryString: (name) => {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substring(1).match(reg);
        if (r != null) return decodeURIComponent(r[2]); return null;
    },
    host:"http://127.0.0.1:1014"
}