package com.zdkj.uilib.dialog.content

import android.content.Context
import android.view.View
import com.zdkj.ktx.ext.gone
import com.zdkj.ktx.ext.visible
import com.zdkj.ktx.utils.onDebouncedClick
import com.zdkj.uilib.R
import com.zdkj.uilib.anno.AddressSelectLevel
import com.zdkj.uilib.anno.AddressSelectLevel.Companion.LEVEL_AREA
import com.zdkj.uilib.anno.AddressSelectLevel.Companion.LEVEL_CITY
import com.zdkj.uilib.bean.AddressBean
import com.zdkj.uilib.databinding.UixDialogContentAddressBinding
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.rv.getAdapter
import com.zdkj.uilib.rv.onItemDebouncedClick
import com.zdkj.uilib.utils.UBiz


/**
 * Address pick dialog content
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-05 10:44
 */
class AddressContent: ViewBindingDialogContent<UixDialogContentAddressBinding>() {

    private lateinit var dialog: BeautyDialog
    private val rvList = mutableListOf<View>()

    @AddressSelectLevel
    private var maxLevel: Int = LEVEL_CITY
    private var listener: ((dialog: BeautyDialog, province: String, city: String?, area: String?) -> Unit)? = null

    override fun doCreateView(ctx: Context) {
        val list = UBiz.getAddressList()

        val pAdapter = getAdapter(R.layout.uix_dialog_content_address_item, { helper, item ->
            helper.setText(R.id.tv, item.name)
        }, list)
        val cityAdapter = getAdapter(R.layout.uix_dialog_content_address_item, { helper, item: AddressBean.CityBean ->
            helper.setText(R.id.tv, item.name)
        }, emptyList<AddressBean.CityBean>().toMutableList())
        val areaAdapter = getAdapter(R.layout.uix_dialog_content_address_item, { helper, item: String ->
            helper.setText(R.id.tv, item)
        }, emptyList<String>().toMutableList())

        rvList.addAll(listOf(binding.rvProvince, binding.rvCity, binding.rvArea))

        binding.rvProvince.adapter = pAdapter
        binding.rvCity.adapter = cityAdapter
        binding.rvArea.adapter = areaAdapter

//        switchToRv(binding.rvProvince)

        pAdapter.onItemDebouncedClick { _, _, pos ->
            val province = list[pos].name
            val cityBeans = list[pos].city
            cityAdapter.setNewInstance(cityBeans as MutableList<AddressBean.CityBean>?)
            binding.tvProvince.text = province
//            switchToRv(binding.rvCity)
            cityAdapter.onItemDebouncedClick { _, _, cityPos ->
                val city = cityBeans!![cityPos].name
                val areaBeans = cityBeans[cityPos].area
                areaAdapter.setNewInstance(areaBeans as MutableList<String>?)
                binding.tvCity.text = city
//                switchToRv(binding.rvArea)
                if (maxLevel == LEVEL_CITY) {
                    listener?.invoke(dialog, province!!, city, null)
                }
                areaAdapter.onItemDebouncedClick { _, _, areaPos ->
                    val area = areaBeans!![areaPos]
                    binding.tvArea.text = area
                    if (maxLevel == LEVEL_AREA) {
                        listener?.invoke(dialog, province!!, city, area)
                    }
                }
            }
        }

        binding.tvProvince.onDebouncedClick {
            binding.tvCity.text = ""
            binding.tvArea.text = ""
//            switchToRv(binding.rvProvince)
        }
        binding.tvCity.onDebouncedClick {
            binding.tvArea.text = ""
//            switchToRv(binding.rvCity)
        }
        binding.tvArea.onDebouncedClick {
//            switchToRv(binding.rvArea)
        }
    }

//    private fun switchToRv(rv: View) {
//        rv.visible()
//        rvList.filter { it != rv }.forEach { it.gone() }
//    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    fun getSelection(): Triple<String?, String?, String?> {
        return Triple<String?, String?, String?>(
                binding.tvProvince.text.toString(),
                binding.tvCity.text.toString(),
                binding.tvArea.text.toString())
    }

    class Builder {
        @AddressSelectLevel private var maxLevel: Int = LEVEL_CITY
        private var listener: ((dialog: BeautyDialog, province: String, city: String?, area: String?) -> Unit)? = null

        fun setMaxLevel(@AddressSelectLevel maxLevel: Int): Builder {
            this.maxLevel = maxLevel
            return this
        }

        fun setOnAddressSelectedListener(listener: (dialog: BeautyDialog, province: String, city: String?, area: String?) -> Unit): Builder {
            this.listener = listener
            return this
        }

        fun build(): AddressContent {
            val content = AddressContent()
            content.maxLevel = maxLevel
            content.listener = listener
            return content
        }
    }
}