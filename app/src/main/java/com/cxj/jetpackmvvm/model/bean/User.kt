package com.cxj.jetpackmvvm.model.bean

import android.os.Parcel
import android.os.Parcelable


data class User(
    val admin: Boolean,
    val chapterTops: java.util.ArrayList<String>?,
    val collectIds: List<Int>?,
    val email: String?,
    val icon: String?,
    val id: Int,
    val nickname: String?,
    val password: String?,
    val publicName: String?,
    val token: String?,
    val type: Int,
    val username: String?
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return if (other is User) {
            this.id == other.id
        } else false
    }

    constructor(source: Parcel) : this(
        1 == source.readInt(),
        source.createStringArrayList(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (admin) 1 else 0))
        writeStringList(chapterTops)
        writeList(collectIds)
        writeString(email)
        writeString(icon)
        writeInt(id)
        writeString(nickname)
        writeString(password)
        writeString(publicName)
        writeString(token)
        writeInt(type)
        writeString(username)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}