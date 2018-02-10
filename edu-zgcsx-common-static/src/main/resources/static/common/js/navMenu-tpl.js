var _tpl = heredoc(function () {
    /*
    <section class="js_winHeight fixed-lr-wrap left-wrap">
        <a class="logo" href="#this" title="">
            <img :attr="{src:@_path + '/static/common/images/logo.png'}" width="160" height="140" alt="">
        </a>
        <menu class="menu-wrap">
            <li :for="(index,el) in @dataList">
                <a :click="@toggleFn($event,el)" :attr="{href:@_path + el.url}" class="title"><i :class="el.imageUrl" class="iconfont icon-jian1"></i>{{el.name}}</a>
                <ul :if="el.subMenu.length" class="item-list">
                    <li :for="(index,ell) in el.subMenu">
                        <a :if="!@isHttp(@ell.url)" :attr="{title:ell.name,href:@_path + ell.url}"
                           :class="[(@menuPath == ell.url ? 'active':'')]">{{ell.name}}</a>
                        <a :if="@isHttp(@ell.url)" :attr="{title:ell.name,href:@ell.url}" target="_blank">{{ell.name}}</a>
                    </li>
                </ul>
            </li>
        </menu>
        <div class="footer-ico">
            <img :attr="{src:@_path + '/static/common/images/footer-ico.png'}" alt="">
        </div>
    </section>
    */
});

var vmNavMenu;
avalon.component("ms-navMenu", {
    template: _tpl,
    defaults: {
        onInit: function (e) {
            vmNavMenu = e.vmodel;
        },
        isShow: false,
        dataList: [],
        _path: '/course',
        menuPath: '',
        toggleFn: function (e, el) {
            e.stopPropagation();
            var _this = $(e.target);
            if (el.url == null || el.url == '') {
                e.preventDefault();
                _this.children('i').toggleClass('icon-jian1').toggleClass('icon-jia');
                _this.siblings('.item-list').slideToggle();
            }
        },
        isHttp: function (href) {
            return href.substring(0, 4) === 'http';
        }
    }
});