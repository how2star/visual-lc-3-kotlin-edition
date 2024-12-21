package tokenizer

/**
 * 本类描述源程序中的一个符号。符号是为语法分析器所准备的最小单元。
 */
data class Token(
    /**
     * 符号的类型。
     */
    val type: TokenType,

    /**
     * 符号所包含的内容，以字符串形式表示。
     */
    val content: String
) {
    /**
     * 将此符号转换为寄存器编号（数字形式）。
     */
    fun asRegisterId(): Int {
        require(type == TokenType.REGISTER) // require 用于判定给定的条件，若不成立则抛出错误

        //TODO("请实现此方法的剩余部分")
        if((content.startsWith("R")||content.startsWith("r")) && content.length > 1){
            val regNum = content.substring(1)
            return regNum.toIntOrNull() ?: throw IllegalArgumentException("无效寄存器编号:$content")
        }else{
            throw IllegalArgumentException("寄存器内容格式无效：$content")
        }
    }

    /**
     * 将此符号转换为字符串内容（去除引号）。
     */
    fun asStringContent(): String {
        require(type == TokenType.STRING)

        //TODO("请实现此方法的剩余部分")
        if (content.startsWith('"') && content.endsWith('"')){
            return content.substring(1,content.length-1)
        }else{
            throw IllegalArgumentException("字符串内容格式无效:$content")
        }
    }

    /**
     * 将此符号转换为其对应的立即数数值（不考虑位数和补码）。
     */
    fun asImmediate(): Int {
        require(type == TokenType.IMMEDIATE)

        return content.first().lowercaseChar().let {
            when (it) {
                '#' -> 10
                'x' -> 16
                else -> throw IllegalArgumentException("未知的进制表示符：$it")
            }
        }.let { content.drop(1).toInt(it) }
    }

    /**
     * 将此符号转换为数字数值（用于 BLKW 指令）。
     */
    fun asNumber(): Int {
        require(type == TokenType.NUMBER)
        return content.toInt()
    }

    /**
     * 将此符号转换为标签名称。
     */
    fun asLabel(): String = content

    /**
     * 将此符号输出为可打印形式（包含类型信息以便调试）。
     */
    fun toFormatted(): String =
        when (type) {
            TokenType.IMMEDIATE -> "I:"
            TokenType.REGISTER -> "R:"
            TokenType.OPERATOR -> "O:"
            TokenType.LABEL -> "L:"
            TokenType.NUMBER -> "N:"
            TokenType.STRING -> "S:"
        } + content

}
