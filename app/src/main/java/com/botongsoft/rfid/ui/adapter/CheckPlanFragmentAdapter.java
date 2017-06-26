package com.botongsoft.rfid.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.common.utils.UIUtils;
import com.botongsoft.rfid.ui.fragment.BaseFragment;

import java.util.List;

/**
 * Created by pc on 2017/6/26.
 * FragmentStatePagerAdapter 和前面的 FragmentPagerAdapter 一样，是继承子 PagerAdapter。但是，和 FragmentPagerAdapter 不一样的是，
 * 正如其类名中的 'State' 所表明的含义一样，该 PagerAdapter 的实现将只保留当前页面，当页面离开视线后，就会被消除，释放其资源；
 * 而在页面需要显示时，生成新的页面(就像 ListView 的实现一样)。这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存。
    getItem()
         一个该类中新增的虚函数。
         函数的目的为生成新的 Fragment 对象。
         Fragment.setArguments() 这种只会在新建 Fragment 时执行一次的参数传递代码，可以放在这里。
         由于 FragmentStatePagerAdapter.instantiateItem() 在大多数情况下，都将调用 getItem() 来生成新的对象，因此如果在该函数中放置与数据集相关的 setter 代码，
         基本上都可以在 instantiateItem() 被调用时执行，但这和设计意图不符。毕竟还有部分可能是不会调用 getItem() 的。因此这部分代码应该放到 instantiateItem() 中。
     instantiateItem()
         除非碰到 FragmentManager 刚好从 SavedState 中恢复了对应的 Fragment 的情况外，该函数将会调用 getItem() 函数，生成新的 Fragment 对象。新的对象将被 FragmentTransaction.add()。
       FragmentStatePagerAdapter 就是通过这种方式，每次都创建一个新的 Fragment，而在不用后就立刻释放其资源，来达到节省内存占用的目的的。
    destroyItem()
         将 Fragment 移除，即调用 FragmentTransaction.remove()，并释放其资源。
 */

public class CheckPlanFragmentAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragments;
    private final String[] titles;

    public CheckPlanFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.titles = UIUtils.getContext().getResources().getStringArray(R.array.check_plandetail_type);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

