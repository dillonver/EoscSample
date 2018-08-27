package com.dillon.eoscsample.utils

import java.util.regex.Matcher
import java.util.regex.Pattern


object RegexUtil {
    /**
     * 手机号码，中间4位星号替换
     *
     * @param phone 手机号
     * @return 星号替换的手机号 string
     */
    fun phoneNoHide(phone: String): String {
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phone.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "$1****$2")
    }

    /**
     * 银行卡号，保留最后4位，其他星号替换
     *
     * @param cardId 卡号
     * @return 星号替换的银行卡号 string
     */
    fun cardIdHide(cardId: String): String {
        return cardId.replace("\\d{15}(\\d{3})".toRegex(), "**** **** **** **** $1")
    }

    /**
     * 身份证号，中间10位星号替换
     *
     * @param id 身份证号
     * @return 星号替换的身份证号 string
     */
    fun idHide(id: String): String {
        return id.replace("(\\d{4})\\d{10}(\\d{4})".toRegex(), "$1** **** ****$2")
    }

    /**
     * 是否为车牌号（沪A88888）
     *
     * @param vehicleNo 车牌号
     * @return 是否为车牌号 boolean
     */
    fun checkVehicleNo(vehicleNo: String): Boolean {
        val pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$")
        return pattern.matcher(vehicleNo).find()

    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkIdCard(idCard: String): Boolean {
        val regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}"
        return Pattern.matches(regex, idCard)
    }

    /**
     * 验证手机格式
     * 验证成功返回true，验证失败返回false
     *
     * @param mobiles the mobiles
     * @return the boolean
     */
    fun isMobileNO(mobiles: String): Boolean {
        /*第一位必定为1，第二位必定为3或4或5或6,7或8,9，其他位置的可以为0-9
    */
        val p = Pattern.compile("^((1[3,4,5,6,7,8,9][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$")
        val m = p.matcher(mobiles)
        return m.matches()
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *
     ***国家（地区） 代码 ：**标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，              数字之后是空格分隔的国家（地区）代码。
     *
     ***区号（城市代码）：**这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——              对不使用地区或城市代码的国家（地区），则省略该组件。
     *
     ***电话号码：**这包含从 0 到 9 的一个或多个数字
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkPhone(phone: String): Boolean {
        val regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$"
        return Pattern.matches(regex, phone)
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkEmail(email: String): Boolean {
        val strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"
        val p = Pattern.compile(strPattern)
        val m = p.matcher(email)
        return m.matches()
    }

    /**
     * 昵稱可由中英文，数字，“_”，“-”组成
     *
     * @param nickName the nick name
     * @return boolean
     */
    fun isNickName(nickName: String): Boolean {
        val strPattern = "^[_\\-a-zA-Z0-9\\u4e00-\\u9fa5]+$"
        val p = Pattern.compile(strPattern)
        val m = p.matcher(nickName)
        return m.matches()
    }

    /**
     * eos账号名
     *
     * @param eosName the eos name
     * @return boolean
     */
    fun isEosName(eosName: String): Boolean {
        val strPattern = "^[a-z]{1}[1-5a-z]{11}$"
        val p = Pattern.compile(strPattern)
        val m = p.matcher(eosName)
        return m.matches()
    }

    /**
     * 搜索输入框限制
     *
     * @param eosName the eos name
     * @return boolean
     */
    fun seachName(eosName: String): Boolean {
        val strPattern = "^[a-z]{1}[1-5a-z]{11}$"
        val p = Pattern.compile(strPattern)
        val m = p.matcher(eosName)
        return m.matches()
    }

    /**
     * 密码设置成6-20位，并且由字母，数字和符号两种以上组合
     *
     * @param pwd the pwd
     * @return boolean
     */
    fun isPwd(pwd: String): Boolean {
        val strPattern = "^[\\w\\u4e00-\\u9fa5]{6,12}$"
        val p = Pattern.compile(strPattern)
        val m = p.matcher(pwd)
        return m.matches()
    }

    /**
     * 验证输入的名字是否为“中文”或者是否包含“·”
     *
     * @param name the name
     * @return the boolean
     */
    fun isLegalName(name: String): Boolean {
        return if (name.contains("·") || name.contains("•")) {
            if (name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$".toRegex())) {
                true
            } else {
                false
            }
        } else {
            if (name.matches("^[\\u4e00-\\u9fa5]+$".toRegex())) {
                true
            } else {
                false
            }
        }
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkDigit(digit: String): Boolean {
        val regex = "\\-?[1-9]\\d+"
        return Pattern.matches(regex, digit)
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkDecimals(decimals: String): Boolean {
        val regex = "\\-?[1-9]\\d+(\\.\\d+)?"
        return Pattern.matches(regex, decimals)
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkBlankSpace(blankSpace: String): Boolean {
        val regex = "\\s+"
        return Pattern.matches(regex, blankSpace)
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkChinese(chinese: String): Boolean {
        val regex = "^[\u4E00-\u9FA5]+$"
        return Pattern.matches(regex, chinese)
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkBirthday(birthday: String): Boolean {
        val regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}"
        return Pattern.matches(regex, birthday)
    }

    /**
     * 验证URL地址
     *
     * @param url
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkURL(url: String): Boolean {
        val regEx = ("^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
                + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
                + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
                + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
                + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
                + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
                + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
                + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$")
        return Pattern.matches(regEx, url)
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkPostcode(postcode: String): Boolean {
        val regex = "[1-9]\\d{5}"
        return Pattern.matches(regex, postcode)
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true ，验证失败返回false
     */
    fun checkIpAddress(ipAddress: String): Boolean {
        val regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))"
        return Pattern.matches(regex, ipAddress)
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private fun isNumeric(str: String): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(str)
        return if (isNum.matches()) {
            true
        } else {
            false
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate the str date
     * @return boolean
     */
    fun isDate(strDate: String): Boolean {
        val regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"
        val pattern = Pattern.compile(regxStr)
        val m = pattern.matcher(strDate)
        return if (m.matches()) {
            true
        } else {
            false
        }
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    fun subZeroAndDot(s: String): String {
        var s = s
        if (s.indexOf(".") > 0) {
            s = s.replace("0+?$".toRegex(), "")//去掉多余的0
            s = s.replace("[.]$".toRegex(), "")//如最后一位是.则去掉
        }
        return s
    }

    /**
     * var phones = {
     * 'ar-DZ': /^(\+?213|0)(5|6|7)\d{8}$/,
     * 'ar-SY': /^(!?(\+?963)|0)?9\d{8}$/,
     * 'ar-SA': /^(!?(\+?966)|0)?5\d{8}$/,
     * 'en-US': /^(\+?1)?[2-9]\d{2}[2-9](?!11)\d{6}$/,
     * 'cs-CZ': /^(\+?420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$/,
     * 'de-DE': /^(\+?49[ \.\-])?([\(]{1}[0-9]{1,6}[\)])?([0-9 \.\-\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$/,
     * 'da-DK': /^(\+?45)?(\d{8})$/,
     * 'el-GR': /^(\+?30)?(69\d{8})$/,
     * 'en-AU': /^(\+?61|0)4\d{8}$/,
     * 'en-GB': /^(\+?44|0)7\d{9}$/,
     * 'en-HK': /^(\+?852\-?)?[569]\d{3}\-?\d{4}$/,
     * 'en-IN': /^(\+?91|0)?[789]\d{9}$/,
     * 'en-NZ': /^(\+?64|0)2\d{7,9}$/,
     * 'en-ZA': /^(\+?27|0)\d{9}$/,
     * 'en-ZM': /^(\+?26)?09[567]\d{7}$/,
     * 'es-ES': /^(\+?34)?(6\d{1}|7[1234])\d{7}$/,
     * 'fi-FI': /^(\+?358|0)\s?(4(0|1|2|4|5)?|50)\s?(\d\s?){4,8}\d$/,
     * 'fr-FR': /^(\+?33|0)[67]\d{8}$/,
     * 'he-IL': /^(\+972|0)([23489]|5[0248]|77)[1-9]\d{6}/,
     * 'hu-HU': /^(\+?36)(20|30|70)\d{7}$/,
     * 'it-IT': /^(\+?39)?\s?3\d{2} ?\d{6,7}$/,
     * 'ja-JP': /^(\+?81|0)\d{1,4}[ \-]?\d{1,4}[ \-]?\d{4}$/,
     * 'ms-MY': /^(\+?6?01){1}(([145]{1}(\-|\s)?\d{7,8})|([236789]{1}(\s|\-)?\d{7}))$/,
     * 'nb-NO': /^(\+?47)?[49]\d{7}$/,
     * 'nl-BE': /^(\+?32|0)4?\d{8}$/,
     * 'nn-NO': /^(\+?47)?[49]\d{7}$/,
     * 'pl-PL': /^(\+?48)? ?[5-8]\d ?\d{3} ?\d{2} ?\d{2}$/,
     * 'pt-BR': /^(\+?55|0)\-?[1-9]{2}\-?[2-9]{1}\d{3,4}\-?\d{4}$/,
     * 'pt-PT': /^(\+?351)?9[1236]\d{7}$/,
     * 'ru-RU': /^(\+?7|8)?9\d{9}$/,
     * 'sr-RS': /^(\+3816|06)[- \d]{5,9}$/,
     * 'tr-TR': /^(\+?90|0)?5\d{9}$/,
     * 'vi-VN': /^(\+?84|0)?((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$/,
     * 'zh-CN': /^(\+?0?86\-?)?1[345789]\d{9}$/,
     * 'zh-TW': /^(\+?886\-?|0)?9\d{8}$/
     */
}
