package com.gco.gco.ui.main

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.gco.gco.MainActivity
import com.gco.gco.R
import com.gco.gco.TypefaceLoader
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.FragmentMainBinding
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.mbt.localization.LocalizedApp
import com.mbt.localization.LocalizedApp.Companion.locale
import com.mbt.utils.viewVisibility
import java.lang.ref.WeakReference

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private val localizationDelegate = LocalizationDelegate()
     var menuSelected:Boolean=false


    companion object {
        private var previousSelectedMenu: Int? = null
        var shared: WeakReference<MainFragment>? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWindowInsets()
        setUpView()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared?.clear()
        shared = WeakReference(this)
    }

    private fun setUpView() {
        binding.apply {
            LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
            dashBackgroundView.setCornerRadius()
            navHostFragment = nestedNavigationContainer.getFragment<NavHostFragment>()
                .apply { navController.addBotNavBarSetter() }

            dashBoardButton.setOnSafeClickListener {
                menuSelected=true
                setStartDestination(R.id.dashboardFragment)

            }
            PerformanceButton.setOnSafeClickListener {
                menuSelected=true
                setStartDestination(R.id.performanceFragment)

            }
            activityButton.setOnSafeClickListener {
                menuSelected=true
                setStartDestination(R.id.activitiesFragment)
            }
            archiveButton.setOnSafeClickListener {
                menuSelected=true
                setStartDestination(R.id.archivesFragment)

            }
            announcementButton.setOnSafeClickListener {
                menuSelected=true
                setStartDestination(R.id.announcementsFragment)
            }
            if (previousSelectedMenu!=null && previousSelectedMenu!= MENU_DASHBOARD)
            {
                dashBoardButtonImage.alpha=1f
            }
        }
    }

    private fun setupWindowInsets() {
        (requireActivity() as MainActivity).windowInsetsLiveData.observe(viewLifecycleOwner) { insets ->
            val rect =
                insets.getInsets(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars() or WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            binding.bottomNavSafeBottomGuide.setGuidelineEnd(rect.bottom)
        }
    }

    private fun findNestedNavController(): NavController = navHostFragment.navController
    private fun NavController.addBotNavBarSetter() = apply {
        addOnDestinationChangedListener { _, destination, _ ->
            val menuCategoryId =
                navMap.firstNotNullOfOrNull { (destinationId, menuCategoryId) ->
                    if (destinationId == destination.id) menuCategoryId else null
                }
            selectBtn(menuCategoryId)
        }
    }

    private fun selectBtn(item: Int? = null) = binding.apply {
        clearBottomMenuSelection()
        when (item) {
            MENU_DASHBOARD -> {
                dashSelectedLayout.viewVisibility(true)
                dashBoardButtonImage.alpha = 0f
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 210L
                    doOnEnd {
                        dashBoardButtonText.setTextColor(LocalizedApp.getColor(R.color.black))
                        dashBoardButtonText.setSemiBold(locale)
                    }
                    start()
                }

                ConstraintSet().apply {
                    clone(bottomNavigationLayout)
                    id.let {
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.START,
                            dashBoardButton.id,
                            ConstraintSet.START,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.END,
                            dashBoardButton.id,
                            ConstraintSet.END,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.TOP,
                            dashBoardButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.BOTTOM,
                            dashBoardButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                    }
                    animateWhileTranslation(this, bottomNavigationLayout, MENU_DASHBOARD)
                }
            }

            MENU_PERFORMANCE -> {
                dashSelectedLayout.viewVisibility(true)
                PerformanceButtonImage.alpha = 0f
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 210L
                    doOnEnd {
                        PerformanceButtonText.setTextColor(LocalizedApp.getColor(R.color.black))
                        PerformanceButtonText.setSemiBold(locale)
                    }
                    start()
                }

                ConstraintSet().apply {
                    clone(bottomNavigationLayout)
                    id.let {
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.START,
                            PerformanceButton.id,
                            ConstraintSet.START,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.END,
                            PerformanceButton.id,
                            ConstraintSet.END,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.TOP,
                            PerformanceButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.BOTTOM,
                            PerformanceButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                    }
                    animateWhileTranslation(this, bottomNavigationLayout, MENU_PERFORMANCE)
                }

            }

            MENU_ACTIVITIES -> {
                dashSelectedLayout.viewVisibility(true)
                activityButtonImage.alpha = 0f
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 210L
                    doOnEnd {
                        activityButtonText.setTextColor(LocalizedApp.getColor(R.color.black))
                        activityButtonText.setSemiBold(locale)
                    }
                    start()
                }

                ConstraintSet().apply {
                    clone(bottomNavigationLayout)
                    id.let {
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.START,
                            activityButton.id,
                            ConstraintSet.START,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.END,
                            activityButton.id,
                            ConstraintSet.END,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.TOP,
                            activityButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.BOTTOM,
                            activityButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                    }
                    animateWhileTranslation(this, bottomNavigationLayout, MENU_ACTIVITIES)
                }

            }

            MENU_ARCHIVES -> {
                dashSelectedLayout.viewVisibility(true)
                archiveButtonImage.alpha = 0f
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 210L
                    doOnEnd {
                        archiveButtonText.setTextColor(LocalizedApp.getColor(R.color.black))
                        archiveButtonText.setSemiBold(locale)
                    }
                    start()
                }

                ConstraintSet().apply {
                    clone(bottomNavigationLayout)
                    id.let {
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.START,
                            archiveButton.id,
                            ConstraintSet.START,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.END,
                            archiveButton.id,
                            ConstraintSet.END,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.TOP,
                            archiveButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.BOTTOM,
                            archiveButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                    }
                    animateWhileTranslation(this, bottomNavigationLayout, MENU_ARCHIVES)
                }

            }

            MENU_ANNOUNCEMENTS -> {
                dashSelectedLayout.viewVisibility(true)
                announcementButtonImage.alpha = 0f
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 210L
                    doOnEnd {
                        announcementButtonText.setTextColor(LocalizedApp.getColor(R.color.black))
                        announcementButtonText.setSemiBold(locale)
                    }
                    start()
                }

                ConstraintSet().apply {
                    clone(bottomNavigationLayout)
                    id.let {
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.START,
                            announcementButton.id,
                            ConstraintSet.START,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.END,
                            announcementButton.id,
                            ConstraintSet.END,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.TOP,
                            announcementButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                        connect(
                            dashSelectedLayout.id,
                            ConstraintSet.BOTTOM,
                            announcementButton.id,
                            ConstraintSet.TOP,
                            0
                        )
                    }
                    animateWhileTranslation(this, bottomNavigationLayout, MENU_ANNOUNCEMENTS)

                }

            }
        }

    }

    private fun animateWhileTranslation(
        constraintSet: ConstraintSet,
        bottomNavigationLayout: ConstraintLayout,
        menuType: Int

    ) {
        previousSelectedMenu = menuType
        val transition = ChangeBounds()
        transition.duration = 200
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
            }


            override fun onTransitionEnd(transition: Transition) {
                binding.apply {
                    when (menuType) {
                        MENU_DASHBOARD -> {
                            dashSelectedImage.setImageDrawable(LocalizedApp.getDrawable(R.drawable.dashbord_icon))
                        }

                        MENU_PERFORMANCE -> {
                            dashSelectedImage.setImageDrawable(LocalizedApp.getDrawable(R.drawable.performance_icon))
                        }

                        MENU_ACTIVITIES -> {
                            dashSelectedImage.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_activities))
                        }

                        MENU_ARCHIVES -> {
                            dashSelectedImage.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_archive))
                        }

                        MENU_ANNOUNCEMENTS -> {
                            dashSelectedImage.setImageDrawable(LocalizedApp.getDrawable(R.drawable.ic_annoncement))
                        }
                    }
                }

            }

            override fun onTransitionCancel(transition: Transition) {}

            override fun onTransitionPause(transition: Transition) {}

            override fun onTransitionResume(transition: Transition) {}
        })
        TransitionManager.beginDelayedTransition(bottomNavigationLayout, transition)

        constraintSet.applyTo(bottomNavigationLayout)


    }

    private fun clearBottomMenuSelection() = binding.apply {
        /*dashBoardButtonImage.viewVisibility(true)
        dashBoardButtonImage.backgroundTintList =
            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
        dashBoardButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))

        PerformanceButtonImage.viewVisibility(true)
        PerformanceButtonImage.backgroundTintList =
            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
        PerformanceButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))
        activityButtonImage.viewVisibility(true)
        activityButtonImage.backgroundTintList =
            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
        activityButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))

        archiveButtonImage.viewVisibility(true)
        archiveButtonImage.backgroundTintList =
            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
        activityButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))
        announcementButtonImage.viewVisibility(true)
        announcementButtonImage.backgroundTintList =
            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
        announcementButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))*/
   if (menuSelected){
       menuSelected=false
        when (previousSelectedMenu) {
            MENU_DASHBOARD -> {
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 220L
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        dashBoardButtonImage.alpha = value
                    }
                    doOnEnd {
                        dashBoardButtonImage.alpha = 1f
                        dashBoardButtonImage.backgroundTintList =
                            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
                        dashBoardButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))
                        dashBoardButtonText.setInterMedium(locale)
                    }
                    start()
                }


            }

            MENU_PERFORMANCE -> {
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 220L
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        PerformanceButtonImage.alpha = value

                    }
                    doOnEnd {
                        PerformanceButtonImage.alpha = 1f
                        PerformanceButtonImage.backgroundTintList =
                            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
                        PerformanceButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))
                        PerformanceButtonText.setInterMedium(locale)

                    }
                    start()
                }

            }

            MENU_ACTIVITIES -> {
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 220L
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        activityButtonImage.alpha = value
                    }
                    doOnEnd {
                        activityButtonImage.alpha = 1f
                        activityButtonImage.backgroundTintList =
                            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
                        activityButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))
                        activityButtonText.setInterMedium(locale)
                    }
                    start()
                }

            }

            MENU_ARCHIVES -> {
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 220L
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        archiveButtonImage.alpha = value
                    }
                    doOnEnd {
                        archiveButtonImage.alpha = 1f
                        archiveButtonImage.backgroundTintList =
                            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
                        archiveButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))
                        archiveButtonText.setInterMedium(locale)
                    }
                    start()
                }

            }

            MENU_ANNOUNCEMENTS -> {
                ObjectAnimator.ofFloat(0f, 1f).apply {
                    duration = 220L
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        announcementButtonImage.alpha = value
                    }
                    doOnEnd {
                        announcementButtonImage.alpha = 1f
                        announcementButtonImage.backgroundTintList =
                            ColorStateList.valueOf(LocalizedApp.getColor(R.color.black_50))
                        announcementButtonText.setTextColor(LocalizedApp.getColor(R.color.black_50))
                        announcementButtonText.setInterMedium(locale)
                    }
                    start()
                }

            }

            else -> {


            }
        }}

    }

     fun setStartDestination(id: Int? = null) {
        when (id) {
            R.id.dashboardFragment, R.id.performanceFragment, R.id.activitiesFragment, R.id.archivesFragment, R.id.announcementsFragment -> {
                val graph = findNestedNavController().navInflater.inflate(R.navigation.nested)
                graph.setStartDestination(id)
                navHostFragment.navController.graph = graph
            }

            /*null -> lifecycleScope.launch {
                Authorization.clear()
                setStartDestination(defaultFragmentId ?: R.id.homeFragment)
                updateReflectionImage()
            }*/
        }
    }

    private inner class LocalizationDelegate : Observer<String> {
        override fun onChanged(locale: String) {
            binding.apply {
                root.layoutDirection=LocalizedApp.getLayoutDirection(locale)
                val resources = LocalizedApp.getResources(locale)
                dashBoardButtonText.apply {
                    text = resources.getString(R.string.txt_dashbord)
                    if (previousSelectedMenu == null || previousSelectedMenu == MENU_DASHBOARD) {
                        setSemiBold(locale)
                    } else {
                        setInterMedium(locale)
                    }
                }
                PerformanceButtonText.apply {
                    text = resources.getString(R.string.txt_performance)
                    if (previousSelectedMenu == MENU_PERFORMANCE) {
                        setSemiBold(locale)
                    } else {
                        setInterMedium(locale)
                    }
                }
                activityButtonText.apply {
                    text = resources.getString(R.string.txt_activities)
                    if (previousSelectedMenu == MENU_ACTIVITIES) {
                        setSemiBold(locale)
                    } else {
                        setInterMedium(locale)
                    }
                }
                archiveButtonText.apply {
                    text = resources.getString(R.string.txt_Archive)
                    if (previousSelectedMenu == MENU_ARCHIVES) {
                        setSemiBold(locale)
                    } else {
                        setInterMedium(locale)
                    }
                }
                announcementButtonText.apply {
                    text = resources.getString(R.string.txt_Announcements)
                    if (previousSelectedMenu == MENU_ANNOUNCEMENTS) {
                        setSemiBold(locale)
                    } else {
                        setInterMedium(locale)
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        previousSelectedMenu = null
    }
}