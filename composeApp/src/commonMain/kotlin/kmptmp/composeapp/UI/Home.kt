package kmptmp.composeapp.UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import kmptmp.composeapp.Theme.ThemeItfc
import kmptmp.composeapp.Theme.ThemeItfc.Companion.cTheme
import kmptmp.composeapp.generated.resources.Res
import kmptmp.composeapp.generated.resources.remind_ful_icon2
import org.jetbrains.compose.resources.painterResource

@Preview @Composable private fun Prev(){Prev{CHome()}}

@Composable fun CHome(){
    Surface(
        color = cTheme.Background,
        contentColor = cTheme.Title,
        content = {
            ConstraintLayout(
                Modifier.fillMaxSize()
            ) {
                val (CLimage, CLtext) = createRefs();

                Image(
                    painterResource( Res.drawable.remind_ful_icon2 )
                    ,
                    null
                    ,
                    Modifier
                        .fillMaxSize()
                        .constrainAs(CLimage,{
                            absoluteLeft.linkTo(parent.absoluteLeft, 5.dp)
                            absoluteRight.linkTo(parent.absoluteRight, 5.dp)
                            top.linkTo(parent.top, 5.dp)
                            bottom.linkTo(parent.bottom, 5.dp)
                        })
                )
                Text(
                    "My crummy template",
                    Modifier.fillMaxWidth()
                        .padding(2.dp)
                        .constrainAs(CLtext,{
                            absoluteLeft.linkTo(parent.absoluteLeft, 5.dp)
                            absoluteRight.linkTo(parent.absoluteRight, 5.dp)
                            top.linkTo(parent.top, 5.dp)
                            bottom.linkTo(parent.bottom, 5.dp)

                            verticalBias = 0.1f
                        })
                        .clickable{
                            ThemeItfc.themeUpdate()
                        }
                    ,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    )
}