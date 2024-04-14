/**
 * @typedef {HTMLElement | SVGElement} KeyIcon
 */
/**
 * Primary and secondary icons
 * @typedef {[KeyIcon, KeyIcon]} IconPair
*/
/**
 * @callback IconDrawer
 * @param {{}} [a1] the attributes of the first icon
 * @param {{}} [a2] the attributes of the second icon
 * @returns {IconPair}
 */
/**
 * @typedef {[IconDrawer, IconDrawer, IconDrawer, IconDrawer, IconDrawer]} CtrlRow
 */
/**
 * @typedef {[CtrlRow, CtrlRow, CtrlRow]} Ctrl
 */
window.sci = {
    mod: 0x100,//modifier; Activates the 'dec', 'deg', 'insert'
    keys: {
        ctrl: [],
        fn: [],
        num: [],
    }
};
window.pro = {
    mod: 0x60A010,//modifier; Activates '8-bit', 'big-endian', 'binary', 'half-even', 'insert'
    keys: {
        ctrl: [],
        fn: [],
        num: [],
    }
};

window.sci.ctrlKeys = /**@param {number} m@returns {Ctrl}*/function(m) {
    /**@type {Ctrl} */
    const ctrl = [];
    ctrl[0] = [
        function(b={}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{AC}\\)";
            return arr;
        },
        function(b = {}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{home}\\)";
            return arr;
        },
        function(b = {}) {
            let arr = [];
            b.stroke ??= "#000000";
            b.fill = "none";
            b["stroke-width"] ??= "2";
            b["stroke-linecap"] ??= "round";
            b["stroke-linejoin"] ??= "round";
            let svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            svg.setAttribute("viewBox", "0 0 24 24");
            svg.setAttribute("fill", "none");
            let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M6 15L12 9L18 15");
            p.setAttribute("stroke", b.stroke);
            p.setAttribute("stroke-width", b["stroke-width"]);
            p.setAttribute("stroke-linecap", b["stroke-linecap"]);
            p.setAttribute("stroke-linejoin", b["stroke-linejoin"]);
            svg.append(p);
            arr[0] = svg;
            return arr;
        },
        function(b = {}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{end}\\)";
            return arr;
        },
        function(a = {}, b = {}) {
            let arr = [];
            a.fill ??= "#000000";
            b.fill ??= "#000000";
            a.stroke = "none";
            b.stroke = "none";
            b["fill-rule"] ??= "evenodd";
            b["clip-rule"] ??= "evenodd";
            arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            arr[0].setAttribute("viewBox", "0 0 24 24");
            arr[0].setAttribute("fill", "none");
            let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M17.7427 8.46448L19.1569 9.87869L17.0356 12L19.157 14.1214L17.7428 15.5356L15.6214 13.4142L13.5 15.5355L12.0858 14.1213L14.2072 12L12.0859 9.87878L13.5002 8.46457L15.6214 10.5858L17.7427 8.46448Z");
            p.setAttribute("fill", a.fill);
            arr[0].append(p);
            p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M8.58579 19L2.29289 12.7071C1.90237 12.3166 1.90237 11.6834 2.29289 11.2929L8.58579 5H22.5857V19H8.58579ZM9.41421 7L4.41421 12L9.41421 17H20.5857V7H9.41421Z");
            p.setAttribute("fill", b.fill);
            p.setAttribute("fill-rule", b["fill-rule"]);
            p.setAttribute("clip-rule", b["clip-rule"]);
            arr[0].append(p);
            return arr;
        }
    ];
    ctrl[1] = [
        function() {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{esc}\\)";
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            b.stroke ??= "#000000";
            b.fill = "none";
            b["stroke-width"] ??= "2";
            b["stroke-linecap"] ??= "round";
            b["stroke-linejoin"] ??= "round";
            arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            arr[0].setAttribute("viewBox", "0 0 24 24");
            arr[0].setAttribute("fill", "none");
            let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M15 6L9 12L15 18");
            p.setAttribute("stroke", b.stroke);
            p.setAttribute("stroke-width", b["stroke-width"]);
            p.setAttribute("stroke-linecap", b["stroke-linecap"]);
            p.setAttribute("stroke-linejoin", b["stroke-linejoin"]);
            arr[0].append(p);
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            switch (m & 0x1f) {
                case 0:{
                    a.stroke ??= "#000000";
                    a.fill = "none";
                    a["stroke-width"] ??= "2";
                    a["stroke-linecap"] ??= "round";
                    a["stroke-linejoin"] ??= "round";
                    b.stroke ??= "#000000";
                    b.fill = "none";
                    b["stroke-width"] ??= "2";
                    b["stroke-linecap"] ??= "round";
                    b["stroke-linejoin"] ??= "round";
                    arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[0].setAttribute("viewBox", "0 0 24 24");
                    arr[0].setAttribute("fill", "none");
                    let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", `M10 8H5V3M5.29102 16.3569C6.22284 17.7918 7.59014 18.8902 9.19218 19.4907C10.7942 20.0913 12.547 20.1624 14.1925 19.6937C15.8379
                    19.225 17.2893 18.2413 18.3344 16.8867C19.3795 15.5321 19.963 13.878 19.9989 12.1675C20.0347 10.4569 19.5211 8.78001 18.5337
                    7.38281C17.5462 5.98561 16.1366 4.942 14.5122 4.40479C12.8878 3.86757 11.1341 3.86499 9.5083 4.39795C7.88252 4.93091 6.47059
                    5.97095 5.47949 7.36556`);
                    Object.keys(a).forEach(k => p.setAttribute(k, a[k]));
                    arr[0].append(p);
                    arr[1] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[1].setAttribute("viewBox", "0 0 24 24");
                    arr[1].setAttribute("fill", "none");
                    p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", `M13.9998 8H18.9998V3M18.7091 16.3569C17.7772 17.7918 16.4099 18.8902 14.8079 19.4907C13.2059 20.0913 11.4534 20.1624 9.80791
                    19.6937C8.16246 19.225 6.71091 18.2413 5.66582 16.8867C4.62073 15.5321 4.03759 13.878 4.00176 12.1675C3.96593 10.4569 4.47903
                    8.78001 5.46648 7.38281C6.45392 5.98561 7.86334 4.942 9.48772 4.40479C11.1121 3.86757 12.8661 3.86499 14.4919 4.39795C16.1177
                    4.93091 17.5298 5.97095 18.5209 7.36556`);
                    Object.keys(b).forEach(k => p.setAttribute(k, b[k]));
                    arr[1].append(p);
                    arr[1].style.height = "1.5em";
                }
                    break;
                case 0x1:{
                    a.stroke ??= "#000000";
                    a.fill = "none";
                    a["stroke-width"] ??= "2";
                    a["stroke-linecap"] ??= "round";
                    a["stroke-linejoin"] ??= "round";
                    b.stroke ??= "#000000";
                    b.fill = "none";
                    b["stroke-width"] ??= "2";
                    b["stroke-linecap"] ??= "round";
                    b["stroke-linejoin"] ??= "round";
                    arr[1] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[1].setAttribute("viewBox", "0 0 24 24");
                    arr[1].setAttribute("fill", "none");
                    let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", `M10 8H5V3M5.29102 16.3569C6.22284 17.7918 7.59014 18.8902 9.19218 19.4907C10.7942 20.0913 12.547 20.1624 14.1925 19.6937C15.8379
                    19.225 17.2893 18.2413 18.3344 16.8867C19.3795 15.5321 19.963 13.878 19.9989 12.1675C20.0347 10.4569 19.5211 8.78001 18.5337
                    7.38281C17.5462 5.98561 16.1366 4.942 14.5122 4.40479C12.8878 3.86757 11.1341 3.86499 9.5083 4.39795C7.88252 4.93091 6.47059
                    5.97095 5.47949 7.36556`);
                    Object.keys(a).forEach(k => p.setAttribute(k, a[k]));
                    arr[1].append(p);
                    arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[0].setAttribute("viewBox", "0 0 24 24");
                    arr[0].setAttribute("fill", "none");
                    p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", `M13.9998 8H18.9998V3M18.7091 16.3569C17.7772 17.7918 16.4099 18.8902 14.8079 19.4907C13.2059 20.0913 11.4534 20.1624 9.80791
                    19.6937C8.16246 19.225 6.71091 18.2413 5.66582 16.8867C4.62073 15.5321 4.03759 13.878 4.00176 12.1675C3.96593 10.4569 4.47903
                    8.78001 5.46648 7.38281C6.45392 5.98561 7.86334 4.942 9.48772 4.40479C11.1121 3.86757 12.8661 3.86499 14.4919 4.39795C16.1177
                    4.93091 17.5298 5.97095 18.5209 7.36556`);
                    Object.keys(b).forEach(k => p.setAttribute(k, b[k]));
                    arr[0].append(p);
                    arr[0].style.height = "1.5em";
                }
                    break;
                default:
            }
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
                    b.stroke ??= "#000000";
                    b.fill = "none";
                    b["stroke-width"] ??= "2";
                    b["stroke-linecap"] ??= "round";
                    b["stroke-linejoin"] ??= "round";
                    arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[0].setAttribute("viewBox", "0 0 24 24");
                    arr[0].setAttribute("fill", "none");
                    let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", "M9 6L15 12L9 18");
                    Object.keys(b).forEach(k => p.setAttribute(k, b[k]));
                    arr[0].append(p);
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{DEL}\\)";
            return arr;
        },
    ];
    ctrl[2] = [
        function() {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{m+}\\)";
            return arr;
        },
        function() {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{mc}\\)";
            return arr;
        },
        function(b = {}) {
            let arr = [];
            b.stroke ??= "#000000";
            b.fill = "none";
            b["stroke-width"] ??= "2";
            b["stroke-linecap"] ??= "round";
            b["stroke-linejoin"] ??= "round";
            arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            arr[0].setAttribute("viewBox", "0 0 24 24");
            arr[0].setAttribute("fill", "none");
            let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M6 9L12 15L18 9");
            Object.keys(b).forEach(k => p.setAttribute(k, b[k]));
            arr[0].append(p);
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{mr}\\)";
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{m-}\\)";
            return arr;
        },
    ];

    return ctrl;
};
window.pro.ctrlKeys = /**@param {number} m*/function(m) {
    /**@type {Ctrl} */
    const ctrl = [];
    ctrl[0] = [
        function(b={}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{AC}\\)";
            return arr;
        },
        function(b = {}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{home}\\)";
            return arr;
        },
        function(b = {}) {
            let arr = [];
            b.stroke ??= "#000000";
            b.fill = "none";
            b["stroke-width"] ??= "2";
            b["stroke-linecap"] ??= "round";
            b["stroke-linejoin"] ??= "round";
            let svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            svg.setAttribute("viewBox", "0 0 24 24");
            svg.setAttribute("fill", "none");
            let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M6 15L12 9L18 15");
            p.setAttribute("stroke", b.stroke);
            p.setAttribute("stroke-width", b["stroke-width"]);
            p.setAttribute("stroke-linecap", b["stroke-linecap"]);
            p.setAttribute("stroke-linejoin", b["stroke-linejoin"]);
            svg.append(p);
            arr[0] = svg;
            return arr;
        },
        function(b = {}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{end}\\)";
            return arr;
        },
        function(a = {}, b = {}) {
            let arr = [];
            a.fill ??= "#000000";
            b.fill ??= "#000000";
            a.stroke = "none";
            b.stroke = "none";
            b["fill-rule"] ??= "evenodd";
            b["clip-rule"] ??= "evenodd";
            arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            arr[0].setAttribute("viewBox", "0 0 24 24");
            arr[0].setAttribute("fill", "none");
            let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M17.7427 8.46448L19.1569 9.87869L17.0356 12L19.157 14.1214L17.7428 15.5356L15.6214 13.4142L13.5 15.5355L12.0858 14.1213L14.2072 12L12.0859 9.87878L13.5002 8.46457L15.6214 10.5858L17.7427 8.46448Z");
            p.setAttribute("fill", a.fill);
            arr[0].append(p);
            p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M8.58579 19L2.29289 12.7071C1.90237 12.3166 1.90237 11.6834 2.29289 11.2929L8.58579 5H22.5857V19H8.58579ZM9.41421 7L4.41421 12L9.41421 17H20.5857V7H9.41421Z");
            p.setAttribute("fill", b.fill);
            p.setAttribute("fill-rule", b["fill-rule"]);
            p.setAttribute("clip-rule", b["clip-rule"]);
            arr[0].append(p);
            return arr;
        }
    ];
    ctrl[1] = [
        function() {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{esc}\\)";
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            b.stroke ??= "#000000";
            b.fill = "none";
            b["stroke-width"] ??= "2";
            b["stroke-linecap"] ??= "round";
            b["stroke-linejoin"] ??= "round";
            arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            arr[0].setAttribute("viewBox", "0 0 24 24");
            arr[0].setAttribute("fill", "none");
            let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M15 6L9 12L15 18");
            p.setAttribute("stroke", b.stroke);
            p.setAttribute("stroke-width", b["stroke-width"]);
            p.setAttribute("stroke-linecap", b["stroke-linecap"]);
            p.setAttribute("stroke-linejoin", b["stroke-linejoin"]);
            arr[0].append(p);
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            switch (m & 7) {
                case 0:{
                    a.stroke ??= "#000000";
                    a.fill = "none";
                    a["stroke-width"] ??= "2";
                    a["stroke-linecap"] ??= "round";
                    a["stroke-linejoin"] ??= "round";
                    b.stroke ??= "#000000";
                    b.fill = "none";
                    b["stroke-width"] ??= "2";
                    b["stroke-linecap"] ??= "round";
                    b["stroke-linejoin"] ??= "round";
                    arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[0].setAttribute("viewBox", "0 0 24 24");
                    arr[0].setAttribute("fill", "none");
                    let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", `M10 8H5V3M5.29102 16.3569C6.22284 17.7918 7.59014 18.8902 9.19218 19.4907C10.7942 20.0913 12.547 20.1624 14.1925 19.6937C15.8379
                    19.225 17.2893 18.2413 18.3344 16.8867C19.3795 15.5321 19.963 13.878 19.9989 12.1675C20.0347 10.4569 19.5211 8.78001 18.5337
                    7.38281C17.5462 5.98561 16.1366 4.942 14.5122 4.40479C12.8878 3.86757 11.1341 3.86499 9.5083 4.39795C7.88252 4.93091 6.47059
                    5.97095 5.47949 7.36556`);
                    Object.keys(a).forEach(k => p.setAttribute(k, a[k]));
                    arr[0].append(p);
                    arr[1] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[1].setAttribute("viewBox", "0 0 24 24");
                    arr[1].setAttribute("fill", "none");
                    p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", `M13.9998 8H18.9998V3M18.7091 16.3569C17.7772 17.7918 16.4099 18.8902 14.8079 19.4907C13.2059 20.0913 11.4534 20.1624 9.80791
                    19.6937C8.16246 19.225 6.71091 18.2413 5.66582 16.8867C4.62073 15.5321 4.03759 13.878 4.00176 12.1675C3.96593 10.4569 4.47903
                    8.78001 5.46648 7.38281C6.45392 5.98561 7.86334 4.942 9.48772 4.40479C11.1121 3.86757 12.8661 3.86499 14.4919 4.39795C16.1177
                    4.93091 17.5298 5.97095 18.5209 7.36556`);
                    Object.keys(b).forEach(k => p.setAttribute(k, b[k]));
                    arr[1].append(p);
                    arr[1].style.height = "1.5em";
                }
                    break;
                case 1:{
                    a.stroke ??= "#000000";
                    a.fill = "none";
                    a["stroke-width"] ??= "2";
                    a["stroke-linecap"] ??= "round";
                    a["stroke-linejoin"] ??= "round";
                    b.stroke ??= "#000000";
                    b.fill = "none";
                    b["stroke-width"] ??= "2";
                    b["stroke-linecap"] ??= "round";
                    b["stroke-linejoin"] ??= "round";
                    arr[1] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[1].setAttribute("viewBox", "0 0 24 24");
                    arr[1].setAttribute("fill", "none");
                    let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", `M10 8H5V3M5.29102 16.3569C6.22284 17.7918 7.59014 18.8902 9.19218 19.4907C10.7942 20.0913 12.547 20.1624 14.1925 19.6937C15.8379
                    19.225 17.2893 18.2413 18.3344 16.8867C19.3795 15.5321 19.963 13.878 19.9989 12.1675C20.0347 10.4569 19.5211 8.78001 18.5337
                    7.38281C17.5462 5.98561 16.1366 4.942 14.5122 4.40479C12.8878 3.86757 11.1341 3.86499 9.5083 4.39795C7.88252 4.93091 6.47059
                    5.97095 5.47949 7.36556`);
                    Object.keys(a).forEach(k => p.setAttribute(k, a[k]));
                    arr[1].append(p);
                    arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[0].setAttribute("viewBox", "0 0 24 24");
                    arr[0].setAttribute("fill", "none");
                    p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", `M13.9998 8H18.9998V3M18.7091 16.3569C17.7772 17.7918 16.4099 18.8902 14.8079 19.4907C13.2059 20.0913 11.4534 20.1624 9.80791
                    19.6937C8.16246 19.225 6.71091 18.2413 5.66582 16.8867C4.62073 15.5321 4.03759 13.878 4.00176 12.1675C3.96593 10.4569 4.47903
                    8.78001 5.46648 7.38281C6.45392 5.98561 7.86334 4.942 9.48772 4.40479C11.1121 3.86757 12.8661 3.86499 14.4919 4.39795C16.1177
                    4.93091 17.5298 5.97095 18.5209 7.36556`);
                    Object.keys(b).forEach(k => p.setAttribute(k, b[k]));
                    arr[0].append(p);
                    arr[0].style.height = "1.5em";
                }
                    break;
                default:
            }
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
                    b.stroke ??= "#000000";
                    b.fill = "none";
                    b["stroke-width"] ??= "2";
                    b["stroke-linecap"] ??= "round";
                    b["stroke-linejoin"] ??= "round";
                    arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    arr[0].setAttribute("viewBox", "0 0 24 24");
                    arr[0].setAttribute("fill", "none");
                    let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    p.setAttribute("d", "M9 6L15 12L9 18");
                    Object.keys(b).forEach(k => p.setAttribute(k, b[k]));
                    arr[0].append(p);
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{DEL}\\)";
            return arr;
        },
    ];
    ctrl[2] = [
        function() {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{m+}\\)";
            return arr;
        },
        function() {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{mc}\\)";
            return arr;
        },
        function(b = {}) {
            let arr = [];
            b.stroke ??= "#000000";
            b.fill = "none";
            b["stroke-width"] ??= "2";
            b["stroke-linecap"] ??= "round";
            b["stroke-linejoin"] ??= "round";
            arr[0] = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            arr[0].setAttribute("viewBox", "0 0 24 24");
            arr[0].setAttribute("fill", "none");
            let p = document.createElementNS("http://www.w3.org/2000/svg", "path");
            p.setAttribute("d", "M6 9L12 15L18 9");
            Object.keys(b).forEach(k => p.setAttribute(k, b[k]));
            arr[0].append(p);
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{mr}\\)";
            return arr;
        },
        function(a={},b={}) {
            let arr = [];
            arr[0] = document.createElement("div");
            arr[0].textContent = "\\(\\mathrm{m-}\\)";
            return arr;
        },
    ];

    return ctrl;
};
/**@typedef {(KeyIcon | undefined)[]} Icons*/
/**
 * @callback IconsDrawer
 * @param {{}} a the atrributes of each returned icon
 * @returns {Icons} 
 */
/**@typedef {[IconsDrawer, IconsDrawer, IconsDrawer, IconsDrawer, IconsDrawer, IconsDrawer]} FnRow */
/**@typedef {[FnRow, FnRow, FnRow]} Fn */
/**@returns {Fn} */
window.sci.fnKeys = function(m) {
    /**@type {Fn} */
    const fn = [];
    fn[0] = [
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sin}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sin}^{-1}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sin}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sin}^{-1}\\)";
                    break;
                case 2:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sinh}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sinh}^{-1}\\)";
                    break;
                case 3:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sinh}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sinh}^{-1}\\)";
                    break;
                case 4:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{csc}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{csc}^{-1}\\)";
                    break;
                case 5:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{csc}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{csc}^{-1}\\)";
                    break;
                case 6:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{csch}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{csch}^{-1}\\)";
                    break;
                case 7:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{csch}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{csch}^{-1}\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{cos}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{cos}^{-1}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{cos}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{cos}^{-1}\\)";
                    break;
                case 2:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{cosh}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{cosh}^{-1}\\)";
                    break;
                case 3:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{cosh}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{cosh}^{-1}\\)";
                    break;
                case 4:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sec}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sec}^{-1}\\)";
                    break;
                case 5:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sec}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sec}^{-1}\\)";
                    break;
                case 6:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sech}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sech}^{-1}\\)";
                    break;
                case 7:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sech}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sech}^{-1}\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{tan}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{tan}^{-1}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{tan}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{tan}^{-1}\\)";
                    break;
                case 2:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{tanh}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{tanh}^{-1}\\)";
                    break;
                case 3:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{tanh}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{tanh}^{-1}\\)";
                    break;
                case 4:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{cot}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{cot}^{-1}\\)";
                    break;
                case 5:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{cot}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{cot}^{-1}\\)";
                    break;
                case 6:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{coth}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{coth}^{-1}\\)";
                    break;
                case 7:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{coth}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{coth}^{-1}\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{e}^{x}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{ln}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{e}^{x}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{ln}\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\sqrt[y]{x}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(x^{y}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\sqrt[y]{x}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(x^{y}\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(|x|\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{sgn}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(|x|\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{sgn}\\)";
                    break;
                default:
            }
            return divs;
        },
    ];

    fn[1] = [
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{atan2}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{lim}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{atan2}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{lim}\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{log}_y^x\\)";
                    divs[0].style.lineHeight = "1.5em"
                    divs[0].style.textAlign = "left";
                    divs[0].style.paddingLeft = ".1em";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(x^{-1}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{log}_y^x\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(x^{-1}\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{7}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{log}_2\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(2^x\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{log}_2\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(2^x\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{8}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{log}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(10^x\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{log}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(10^x\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{9}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(x^3\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\sqrt[3]{x}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(x^3\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\sqrt[3]{x}\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{0}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\sqrt{x}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(x^2\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\sqrt{x}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(x^2\\)";
                    break;
                default:
            }
            return divs;
        },
    ];

    fn[2] = [
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\sum\\)";
                    divs[0].style.lineHeight = "1.5em";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\prod\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\sum\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\prod\\)";
                    divs[0].style.lineHeight = "1.5em";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{1}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\Gamma(x)\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{erf}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\Gamma(x)\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{erf}\\)";
                    divs[0].style.lineHeight = "2.1em";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{2}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathtt{\\int}\\mathrm{d}x\\)";
                    divs[0].style.lineHeight = "1.5em";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\frac{\\mathrm{d}}{\\mathrm{d}x}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathtt{\\int}\\mathrm{d}x\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\frac{\\mathrm{d}}{\\mathrm{d}x}\\)";
                    divs[0].style.lineHeight = "1.4em";
                    divs[0].style.textAlign = "left";
                    divs[0].style.paddingLeft = ".8em";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{3}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{Pol}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{Rec}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{Pol}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{Rec}\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{4}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{Re}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{Im}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{Re}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{Im}\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{5}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            /*only retrive the shift modifier */
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{z}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\theta\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\overline{z}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\theta\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\overline{6}\\)";
                    break;
                }
                default:
            }
            return divs;
        },
    ];

    return fn;
}
/**@returns {Fn} */
window.pro.fnKeys = /**@param {bigint}m*/function(m) {
    /**@type {Fn} */
    const fn = [];
    fn[0] = [
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "sin";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "asin";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "sin";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "asin";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "M";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "cos";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "acos";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "cos";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "acos";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "N";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "tan";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "atan";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "tan";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "atan";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "O";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "log";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "exp";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "log";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "exp";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "P";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "pow";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "sqrt";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "pow";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "sqrt";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "Q";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "log10";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "cbrt";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "log10";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "cbrt";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "R";
                    break;
                }
                default:
            }
            return divs;
        },
    ];
    fn[1] = [
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "trunc";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "rint";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "trunc";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "rint";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "G";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "nextDown";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "nextUp";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "nextDown";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "nextUp";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "H";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "ulp";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "nextAftr";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "ulp";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "nextAftr";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "I";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "Y";
                    break;
                }
                case 5:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "I";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "Y";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "ieeeRem";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "round";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "ieeeRem";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "round";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "J";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "Z";
                    break;
                }
                case 5:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "J";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "Z";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "floor";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "ceil";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "floor";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "ceil";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "K";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "sigfc";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "expt";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "sigfc";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "expt";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "L";
                    break;
                }
                default:
            }
            return divs;
        },
    ];
    fn[2] = [
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "low";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "high";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "low";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "high";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "A";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "S";
                    break;
                }
                case 5:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "A";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "S";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "nth";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "on";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "nth";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "on";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "B";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "T";
                    break;
                }
                case 5:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "B";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "T";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "count";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "abs";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "count";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "abs";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "C";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "U";
                    break;
                }
                case 5:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "C";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "U";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "max";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "min";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "max";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "min";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "D";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "V";
                    break;
                }
                case 5:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "D";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "V";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "nand";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "signum";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "nand";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "signum";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "E";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "W";
                    break;
                }
                case 5:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "E";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "W";
                    break;
                }
                default:
            }
            return divs;
        },
        function() {
            let divs = [];
            switch (m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "xnor";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "nor";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "xnor";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "nor";
                    break;
                }
                case 4:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "F";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "X";
                    break;
                }
                case 5:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "F";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "X";
                    break;
                }
                default:
            }
            return divs;
        },
    ];
    return fn;
}
/**@typedef {[IconsDrawer, IconsDrawer, IconsDrawer, IconsDrawer, IconsDrawer]} NumRow */
/**@typedef {[NumRow, NumRow, NumRow, NumRow]} Num */
/**@returns {Num} */
window.sci.numKeys = function(m) {
    /**@type {Num} */
    const num = [];

    num[0] = [
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(7\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(k!\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(7\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(k!\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\pi\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\Omega\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\pi\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\Omega\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(7\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(8\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(nCk\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(8\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(nCk\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(e\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{L}_c\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(e\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{L}_c\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(8\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(9\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(nPk\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(9\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(nPk\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\sqrt{2}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\sqrt{3}\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\sqrt{2}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\sqrt{3}\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(9\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\((\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(x\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\((\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(x\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\()\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(y\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\()\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(y\\)";
                    break;
                default:
            }
            return divs;
        }
    ];
    num[1] = [
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(4\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{min}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(4\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{min}\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\tau\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\zeta(3)\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\tau\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\zeta(3)\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(4\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(5\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{max}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(5\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{max}\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\phi\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\psi_S\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\phi\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\psi_S\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(5\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(6\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\Delta\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(6\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\Delta\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\gamma\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\delta_S\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\gamma\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\delta_S\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(6\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\times\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\lceil x\\rceil\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\times\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\lceil x\\rceil\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\div\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\lfloor x\\rfloor\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\div\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\lfloor x\\rfloor\\)";
                    break;
                default:
            }
            return divs;
        },
    ];
    num[2] = [
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(1\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{min}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(1\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{min}\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{M}\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\lambda_2\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{M}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\lambda_2\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(1\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(2\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{gcd}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(2\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{gcd}\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\sigma\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\lambda\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\sigma\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\lambda\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(2\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(3\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{lcm}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(3\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{lcm}\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\theta\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\rho\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\theta\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\rho\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(3\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(+\\)";
                    break;
                case 1:
                default:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(+\\)";
                    // break;
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(-\\)";
                    break;
                case 1:
                default:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(-\\)";
                    // break;
            }
            return divs;
        }
    ];
    num[3] = [
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\frac{x}{y}\\)";
                    divs[0].style.lineHeight = "1.2em";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{Z}\\frac{x}{y}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\frac{x}{y}\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{Z}\\frac{x}{y}\\)";
                    divs[0].style.lineHeight = "1.2em";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(0\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\#\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(0\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\#\\)";
                    break;
                case 8:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mu\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{L}_y\\)";
                    break;
                case 9:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mu\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{L}_y\\)";
                    break;
                case 16:
                case 17:{
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(0\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 16:
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(.\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\times10^x\\)";
                    break;
                case 17:
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(.\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\times10^x\\)";
                    break;
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\pm\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\%\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\pm\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\%\\)";
                    break;
                case 16:
                case 17: {
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\pm\\)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(a) {
            let divs = [];
            switch (m & 0x1f) {
                case 0:
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(=\\)";
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(\\mathrm{Ans}\\)";
                    break;
                case 1:
                    divs[1] = document.createElement("div");
                    divs[1].textContent = "\\(=\\)";
                    divs[0] = document.createElement("div");
                    divs[0].textContent = "\\(\\mathrm{Ans}\\)";
                    break;
                default:
            }
            return divs;
        },
    ];

    return num;
}
window.pro.numKeys = /**@param {bigint}m*/function(m){
    /**@type {Num}*/
    const num = [];
    num[0] = [
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "7";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "A";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "7";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "A";
                    break;
                }
                case 2:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "Infin";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "7";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "8";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "B";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "8";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "B";
                    break;
                }
                case 2:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "NaN";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "8";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "9";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "C";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "9";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "C";
                    break;
                }
                case 2:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "MIN";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "9";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "(";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "<<";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "(";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "<<";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = ")";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = ">>";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = ")";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = ">>";
                    break;
                }
                default:
            }
            return divs;
        },
    ];
    num[1] = [
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "4";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "D";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "4";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "D";
                    break;
                }
                case 2:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "MAX";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "4";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "5";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "E";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "5";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "E";
                    break;
                }
                case 2:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "PI";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "5";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "6";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "F";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "6";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "F";
                    break;
                }
                case 2:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "E";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "6";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "*";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "^";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "*";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "^";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "/";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "%";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "/";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "%";
                    break;
                }
                default:
            }
            return divs;
        },
    ];
    num[2] = [
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "1";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "$";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "1";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "$";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "1";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "2";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "++";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "2";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "++";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "2";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "3";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "--";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "3";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "--";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "3";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "+";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "|";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "+";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "|";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "-";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "&";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "-";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "&";
                    break;
                }
                default:
            }
            return divs;
        },
    ];
    num[3] = [
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "mod";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "p";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "mod";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "p";
                    break;
                }
                case 5:
                case 4: {
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "p";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "0";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "#";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "0";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "#";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "0";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 4:
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = ".";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "e";
                    break;
                }
                case 5:
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = ".";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "e";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "(-)";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "~";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "(-)";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "~";
                    break;
                }
                case 4:
                case 5:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "(-)";
                    break;
                }
                default:
            }
            return divs;
        },
        function(){
            let divs = [];
            switch(m & 7) {
                case 0:{
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "=";
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "Ans";
                    break;
                }
                case 1:{
                    divs[1] = document.createElement("div");
                    divs[1].classList.add("mono");
                    divs[1].textContent = "=";
                    divs[0] = document.createElement("div");
                    divs[0].classList.add("mono");
                    divs[0].textContent = "Ans";
                    break;
                }
                default:
            }
            return divs;
        },
    ];
    return num;
}
// control buttons
function load(calc = "sci") {
    switch (calc) {
        case "sci":
        case "pro":
        default: {
            (function(type){
                const c = window[type].ctrlKeys(window[type].mod);
                /**@type {HTMLDivElement} */
                const ctl = document.querySelector(`.interface[data-id=${type}] div.ctrl`);
                
                for(let i = 0; i < 15; i++) {
                    const row = Math.trunc(i / 5);
                    const col = i - (row * 5);
            
                    let key = document.getElementById(`${type}-ctrl-${i + 1}`);
                    if(!key){
                        key = document.createElement("div");
                        key.classList.add("key");
                        key.id = `${type}-ctrl-${i + 1}`;
                        ctl.append(key);
                    } else {
                        while (key.firstChild) key.removeChild(key.lastChild);
                    }
                    let pri, sec;
                    const pair = c[row][col](/*{stroke: "blue", fill: "blue"}*/);
                    if(pair[0]){
                        pri = pair[0];
                        pri.classList.add("pri");
                    }
                    if(pair[1]) {
                        sec = pair[1];
                        sec.classList.add("sec");
                    }
                
                    if(sec) key.append(sec);
                    if(pri) key.append(pri);
                }
            })(calc);
            
            (function(type){
                const f = window[type].fnKeys(window[type].mod);
                /**@type {HTMLDivElement} */
                const fn = document.querySelector(`.interface[data-id=${type}] div.fn`);
            
                for (let i = 0; i < 18; i++) {
                    const row = Math.trunc(i / 6);
                    const col = i - (row * 6);
            
                    let key = document.getElementById(`${type}-fn-${i + 1}`);
                    if(!key){
                        key = document.createElement("div");
                        key.classList.add("key");
                        key.id = `${type}-fn-${i + 1}`;
                        fn.append(key);
                    } else {
                        while (key.firstChild) key.removeChild(key.lastChild);
                    }
                    let k0, k1;
                    const p = f[row][col]();
                    if(p[0]) k0 = p[0];
                    if(p[1]) k1 = p[1];

                    if(k0) {
                        k0.classList.add("pri");
                        key.append(k0);
                    }
                    if(k1) {
                        k1.classList.add("sec");
                        key.append(k1);
                    }
                }
            })(calc);
            
            (function(type){
                const f = window[type].numKeys(window[type].mod);
                /**@type {HTMLDivElement} */
                const num = document.querySelector(`.interface[data-id=${type}] div.num`);
            
                for (let i = 0; i < 20; i++) {
                    const row = Math.trunc(i / 5);
                    const col = i - (row * 5);
            
                    let key = document.getElementById(`${type}-num-${i + 1}`);
                    if(!key){
                        key = document.createElement("div");
                        key.classList.add("key");
                        key.id = `${type}-num-${i + 1}`;
                        num.append(key);
                    } else {
                        while (key.firstChild) key.removeChild(key.lastChild);
                    }
                    let k0, k1;
                    const p = f[row][col]();
                    if(p[0]) k0 = p[0];
                    if(p[1]) k1 = p[1];

                    if(k0) {
                        k0.classList.add("pri");
                        key.append(k0);
                    }
                    if(k1) {
                        k1.classList.add("sec");
                        key.append(k1);
                    }
                }
            })(calc);
        }
    }
    MathJax.typeset();
}
/**@param {HTMLButtonElement} e  */
function switchTab(e){
    document.querySelectorAll('.tab').forEach(b => b.classList.remove('current'));
    e.classList.add('current');
    document.querySelectorAll('.interface').forEach(b => b.classList.remove('active'));
    document.querySelector(`[data-id=${e.dataset.for}]`).classList.add('active');
    load(e.dataset.for);
}
/**@param {HTMLElement} e @param {number} mod @param {string} [calc="sci"]  */
function priMod(e, mod, calc="sci") {
    e.classList.toggle("active");
    window[calc].mod^=mod;
    load(calc);

    // console.log(binary(window[calc].mod.toString(2), 32), mod);
}
/**
 * Updates the data attribute of the given element by incrementing it and settings the
 * `textContent` to the element at the index specified by the attribute.
 * @note all groups are small-endian. A boolean group is a group of adjacent bits
 * whereby only a single bit in that group can ever be set (`1`). This is the opposite of
 * non-boolean groups where multiple bits within the group can be set.
 * The following is a Scientific calculator example table describing this:
 * 
 * |Modifier-type|Modifier-name|Modifier-index|Group-index|Group-name|Description|
 * |:------------|:------------:|:------------:|:------------:|------------:|:----------:|
 * |non-boolean|shift|`0`|0|primary modifier|toggles the second function with keys|
 * |non-boolean|hyp|`1`|0|primary modifier|toggles the trigonometric hyperbolic keys. When shift is **on**, this toggles the inverse hyperbolic functions|
 * |non-boolean|rcl|`2`|0|primary modifier|toggles the reciprocal trigonometric keys (csc, sec, cot). When shift is **on**, this toggles the inverse reciprocals |
 * |non-boolean|caps-lock|`3`|0|primary modifier|toggles the quick access constant keys. When shift is **on**, this toggles quick access to user-defined variables|
 * |non-boolean|num-lock|`4`|0|primary modifier|toggles the recurring digit keys|
 * |non-boolean|expr|`5`|0|primary modifier|enables/disables the expression-type results. e.g if a constant (such as π) is evaluated, its numerical value will be replaced with the variable itself when this modifier is **on**|
 * |non-boolean|comp|`6`|0|primary modifier|enables/disables the complex-type results and calculations|
 * |non-boolean|suffix|`7`|0|primary modifier|enables/disables the engineering symbolic prefixes (such as **μ**). Note that this is only useful if result-type is set to **ENG**|
 * |boolean|result-type|`8`-`14`|1|secondary modifier|determines the type of result displayed to the user. These includes: `dec`, `frac`, `mfrac`, `fix`, `sci`, `eng`|
 * |boolean|trig|`14`-`16`|2|secondary modifier|determines the unit used for trigonometric calculations. The include: `deg`, `rad`, `grad`|
 * |boolean|edit|`16`-`20`|3|secondary modifier|determines the input method. The include: `insert`, `overwrite`, `append`, `prepend`|
 * 
 * @param {HTMLElement} e the caller of this method
 * @param {string[]} mods the names of the modifiers to switch between
 * @param {string} dataAttr the name of the `data-*` attribute to be updated with each call
 * @param {number} index a value that helps with modifier bit lookup. This the index within the total modifier where these set of modifiers begin.
 * @param {string} [calc="sci"] The calculator from which to perform this action.
 * @param {number} [primaryMod=8] How many primary modificators are there in this calculator. This is also the total number of bits the primary modifiers use
 */
function secMod(e, mods, dataAttr, index, calc = "sci", primaryMod = 8){
    let i=Number.parseInt(e.dataset[dataAttr]);
    i=i>=(mods.length-1)?0:(i+1);
    e.dataset[dataAttr]=String(i);
    e.textContent=mods[i].toUpperCase();
    let mod = 1 << i;
    //extract all bits to the left of this
    const left = window[calc].mod >>> (primaryMod + index + mods.length);
    //extract all bits to the right of this
    const right = Array.apply(null, Array(primaryMod + index)).reduce(p => (p << 1) | 1, 0) & window[calc].mod;
    window[calc].mod = ((left << (primaryMod + index + mods.length)) | ((mod << (primaryMod + index)) | right)) >>> 0;

    // console.log(binary(window[calc].mod.toString(2), 32), mod);
}
/**
 * @param {HTMLElement} e 
 * @param {string[]} mods 
 * @param {string} dataAttr 
 * @param {number} index 
 * @param {string} [calc="sci"] 
 * @param {number} [pri=8]
 * @param {number} [sec=2]
 * @returns {void}
 */
function triMod(e, mods, dataAttr, index, calc = "sci", pri = 8, sec = 2) {
    let i=Number.parseInt(e.dataset[dataAttr]);
    i=i>=(mods.length-1)?0:(i+1);
    e.dataset[dataAttr]=String(i);
    e.textContent=mods[i].toUpperCase();
    let mod = i;
    //mods must have more than 1 element or an undefined behavior will occur
    let l = bl(mods.length-1);
    //extract all bits to the left of this
    const left = window[calc].mod >>> (pri + sec + index + l);
    //extract all bits to the right of this
    const right = Array.apply(null, Array(pri + sec + index)).reduce(p => (p << 1) | 1, 0) & window[calc].mod;
    window[calc].mod = ((left << (pri + sec + index + l)) | ((mod << (pri + sec + index)) | right)) >>> 0;

    // console.log(binary(window[calc].mod.toString(2), 32), mod);
}
/**Used by the element that displays the radix modifier @param {HTMLElement} e*/
function rax(e) {
    let mods = ["bin","oct","dec","hex"];
    let dataAttr = "im";
    let index = 8;
    let calc = "pro";
    let pri = 4;
    let sec = 2;
    let i=Number.parseInt(e.dataset[dataAttr]);
    i=i>=(mods.length-1)?0:(i+1);
    e.dataset[dataAttr]=String(i);
    e.textContent=mods[i].toUpperCase();
    const mod = ([2,8,10,16])[i];
    //mods must have more than 1 element or an undefined behavior will occur
    const l = bl(36);
    //extract all bits to the left of this
    const left = window[calc].mod >>> (pri + sec + index + l);
    //extract all bits to the right of this
    const right = Array.apply(null, Array(pri + sec + index)).reduce(p => (p << 1) | 1, 0) & window[calc].mod;
    window[calc].mod = ((left << (pri + sec + index + l)) | ((mod << (pri + sec + index)) | right)) >>> 0;

    // console.log(binary(window[calc].mod.toString(2), 32), mod);
}
/**
 * @param {number} i 
 * @returns {number}
 */
function bl(i) {
    let l = 0;
    if(typeof i === "number"){
        while(i>0) {
            i>>>=1;
            l++;
        }
    }
    return l;
}
/**
 * @param {number} i 
 * @returns {number}
 */
function ones(i) {
    return  Array.apply(null, Array(i)).reduce(p => (p << 1) | 1, 0);
    // let n = 0;
    // if(typeof i === "number"){
    //     n = 1;
    //     while(i>0) {
    //         n = n << 1;
    //         i--;
    //     } 
    // }
    // return n;
}
/**@param {string} s @param {number} l @param {string} [c] @param {(x:string, s:string)=>string} [f]  */
function binary(s,l,c='0',f=(x,y)=>x+y){
    if(s.length < l){
        let x = l - s.length;
        s = f(c.repeat(l-s.length), s);
    }
    return s;
}

function loadUI(calc = "sci") {

    p = document.createElement("div");
    p.classList.add("pad");
    let c = document.createElement("div");
    c.classList.add("keys", "ctrl");
    p.appendChild(c);
    c = document.createElement("div");
    c.classList.add("keys", "fn");
    p.appendChild(c);
    c = document.createElement("div");
    c.classList.add("keys", "num");
    p.appendChild(c);

    c = document.createElement("div");
    c.classList.add("interface", "active");
    c.dataset.id = calc;

    c.appendChild(p);
    // p = c;

    let p = document.createElement('div');
    p.classList.add("mods", "top");
    c = document.createElement('div');
    c.classList.add("mod");
    c.addEventListener("click", e => priMod(e.target, 1));
    c.style.width = "4em"
    c.textContent = "SHIFT";
    p.appendChild(c);
    c = document.createElement('div');
    c.classList.add("mod");
    c.addEventListener("click", e => priMod(e.target, 2));
    c.style.width = "3em"
    c.textContent = "HYP";
    p.appendChild(c);
    c = document.createElement('div');
    c.classList.add("mod");
    c.addEventListener("click", e => priMod(e.target, 4));
    c.style.width = "3em"
    c.textContent = "RCL";
    p.appendChild(c);
    c = document.createElement('div');
    c.classList.add("mod");
    c.addEventListener("click", e => priMod(e.target, 8));
    c.style.width = "5.8em"
    c.textContent = "CAPS-LOCK";
    p.appendChild(c);
    c = document.createElement('div');
    c.classList.add("mod");
    c.addEventListener("click", e => priMod(e.target, 16));
    c.style.width = "5.8em"
    c.textContent = "NUM-LOCK";
    p.appendChild(c);

    c = document.createElement("div");
    c.classList.add("displays");
    c.appendChild(p);
    p = c;

    c = document.createElement("div");
    c.classList.add("in", "display");
    c.appendChild(document.createElement("div")).classList.add("lcd");
    c.firstChild.textContent = `\\[ x= \\frac{\\textrm{-}b\\pm \\sqrt{\\cssId{caret}{\\left|b ^{2
    }\\right.}-4ac } }{2a} \\]`;
    p.appendChild(c);

    c = document.createElement("div");
    c.classList.add("msg", "display", "info");
    c.appendChild(document.createElement("div")).classList.add("lcd");
    c.firstChild.textContent = "No Error(s) detected";
    p.appendChild(c);

    c = document.createElement("div");
    c.classList.add("out", "display");
    c.appendChild(document.createElement("div")).classList.add("lcd");
    c.firstChild.textContent = "\\[ \\frac{200x}{50y^2} \\]";
    p.appendChild(c);

    c = document.createElement("div");
    c.classList.add("mods", "bottom");
    c.appendChild(document.createElement("div")).classList.add("mod", "active");
    c.firstChild.textContent = "DEC";
    c.firstChild.style.width = "4em";
    c.firstChild.dataset.im = "0";
    c.firstChild.addEventListener("click", e => secMod(e.target, ["dec", "frac", "mfrac", "fix", "sci", "eng"], "im", 0));
    p.appendChild(c);

    c.appendChild(document.createElement("div")).classList.add("mod");
    c.lastChild.textContent = "EXPR";
    c.lastChild.style.width = "3em";
    c.lastChild.addEventListener("click", e => priMod(e.target, 32));
    
    c.appendChild(document.createElement("div")).classList.add("mod", "active");
    c.lastChild.textContent = "DEG";
    c.lastChild.style.width = "3em";
    c.lastChild.dataset.im = "0";
    c.lastChild.addEventListener("click", e => secMod(e.target, ["deg", "rad", "grad"], "im", 6));

    c.appendChild(document.createElement("div")).classList.add("mod");
    c.lastChild.textContent = "COMP";
    c.lastChild.style.width = "3em";
    c.lastChild.addEventListener("click", e => priMod(e.target, 64));

    c.appendChild(document.createElement("div")).classList.add("mod");
    c.lastChild.textContent = "SUFFIX";
    c.lastChild.style.width = "4em";
    c.lastChild.addEventListener("click", e => priMod(e.target, 128));
    
    c.appendChild(document.createElement("div")).classList.add("mod", "active");
    c.lastChild.textContent = "INSERT";
    c.lastChild.style.width = "6.5em";
    c.lastChild.dataset.im = "0";
    c.lastChild.addEventListener("click", e => secMod(e.target, ["insert", "overwrite", "prepend", "append"], "im", 9));
}
// loadUI();
load();
// load("pro");

// MathJax.typesetPromise();
