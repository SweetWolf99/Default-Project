package kr.kro.minestar.murder.creature.slayer.job

enum class SlayerList {
    SLAUGHTER
    ;
    override fun toString():String{
        val arr: CharArray = name.toLowerCase().toCharArray()
        arr[0] = Character.toUpperCase(arr[0])
        return String(arr)
    }
}