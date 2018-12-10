package controllers

import apache.cayenne.mappings.Solution
import apache.cayenne.mappings.Subtheme
import apache.cayenne.mappings.Task
import apache.cayenne.mappings.Theme
import com.google.gson.Gson
import com.trainer.OrmManager
import com.trainer.controllers.UserController
import org.apache.cayenne.Cayenne
import org.apache.cayenne.query.ObjectSelect
import java.time.LocalDate
import java.util.*
import javax.management.Query.eq
import sun.misc.MessageUtils.where



object StatsController {
    fun getChartData(startDate: LocalDate, endDate: LocalDate, userId:Int, themeNames: List<String>): String{

        val context = OrmManager.newContext()

        val solutions =
            ObjectSelect
                .query(Solution::class.java)
                .select(context)

        val filteredSolutions = solutions.filter {
                Cayenne.intPKForObject(it.user1) == userId &&
                it.solutionDateTime.toLocalDate() >= startDate &&
                it.solutionDateTime.toLocalDate() <= endDate &&
                it.task.subtheme1.theme.name in themeNames
        }

        var resultList: MutableList<String> = mutableListOf()

        themeNames.forEach(fun(theme:String){
            val curThemeSolutions = filteredSolutions.filter { it.task.subtheme1.theme.name == theme }
            var totalCount = curThemeSolutions.count()
            var curThemeRightSolutions = curThemeSolutions.filter { it.actualAnswer == it.task.rightAnswer }
            var rightCount = curThemeRightSolutions.count()
            val themeDescription =
                ObjectSelect
                    .query(Theme::class.java)
                    .where(Theme.NAME.eq(theme))
                    .select(context)[0]
                    .description
            resultList.add("[\"${themeDescription}\", ${totalCount}, ${rightCount}]")
        })

        val result = resultList.joinToString(prefix = "[[\"Предмет\", \"Всего\", \"Верно\"], ", postfix = "]")

        return result
    }
}
