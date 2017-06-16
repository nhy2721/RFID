package com.botongsoft.rfid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.ui.activity.MainActivity;
import com.botongsoft.rfid.ui.adapter.CategoryAdapter;
import com.botongsoft.rfid.ui.widget.RecyclerViewDecoration.SpacesItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/7/13
 * Description:
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
//    @BindView(R.id.viewPager)
//    ViewPager mViewPager;
//    @BindView(R.id.tabLayout)
//    TabLayout mTabLayout;
//    @BindView(R.id.fab)
//    FloatingActionButton mFab;
    private List<BaseFragment> fragments;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private CategoryAdapter mCategoryAdapter;
    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    protected void initEvents() {
        //添加装饰器
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        //设置布局管理器
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置adapter
        mCategoryAdapter = new CategoryAdapter(getActivity());
        mRecyclerView.setAdapter(mCategoryAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData(boolean isSavedNull) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        ((MainActivity) getActivity()).setToolbar(mToolbar);
//        ((MainActivity) getActivity()).setFab(mFab);
    }

    private void init() {
//        fragments = new ArrayList<>();
//        fragments.add(BookListFragment.newInstance("新书"));
//        fragments.add(BookListFragment.newInstance("热门"));
//        fragments.add(BookListFragment.newInstance("推荐"));
//        fragments.add(CategoryFragment.newInstance());
//        fragments.add(BookListFragment.newInstance("小说"));
//        fragments.add(DiscoverFragment.newInstance());

//        mViewPager.setAdapter(new MainAdapter(getChildFragmentManager(), fragments));
//        mViewPager.setOffscreenPageLimit(1);//设置加载几个标签
//        mViewPager.setCurrentItem(0);//设置默认显示位置
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setSelectedTabIndicatorColor(getContext().getResources().getColor(R.color.white));
    }

    class MainAdapter extends FragmentStatePagerAdapter {
        private List<BaseFragment> mFragments;
//        private final String[] titles;

        public MainAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
//            this.titles = getResources().getStringArray(R.array.main_tab_type);
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
//            return titles[position];
            return "";
        }
    }
}
