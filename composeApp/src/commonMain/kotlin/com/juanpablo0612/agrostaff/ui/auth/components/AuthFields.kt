package com.juanpablo0612.agrostaff.ui.auth.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.confirm_password_error_text
import agrostaff.composeapp.generated.resources.confirm_password_label_text
import agrostaff.composeapp.generated.resources.email_error_text
import agrostaff.composeapp.generated.resources.email_label_text
import agrostaff.composeapp.generated.resources.password_error_text
import agrostaff.composeapp.generated.resources.password_label_text
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.juanpablo0612.agrostaff.ui.components.BaseTextField
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Next
) {
    val supportingText = if (isError) {
        @Composable {
            Text(
                text = stringResource(Res.string.email_error_text)
            )
        }
    } else null

    BaseTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        label = {
            Text(stringResource(Res.string.email_label_text))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Mail,
                contentDescription = null
            )
        },
        supportingText = supportingText,
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = imeAction),
        singleLine = true
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    isError: Boolean,
    showPassword: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
) {
    val supportingText = if (isError) {
        @Composable {
            Text(
                text = stringResource(Res.string.password_error_text)
            )
        }
    } else null

    BaseTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        label = {
            Text(stringResource(Res.string.password_label_text))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Key,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityChange) {
                AnimatedContent(
                    targetState = showPassword,
                    transitionSpec = {
                        if (targetState) {
                            slideInVertically { height -> height } + fadeIn() togetherWith
                                    slideOutVertically { height -> -height } + fadeOut()
                        } else {
                            slideInVertically { height -> -height } + fadeIn() togetherWith
                                    slideOutVertically { height -> height } + fadeOut()
                        }
                    }
                ) { targetState ->
                    if (targetState) {
                        Icon(
                            imageVector = Icons.Outlined.VisibilityOff,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        supportingText = supportingText,
        isError = isError,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        singleLine = true
    )
}

@Composable
fun ConfirmPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    isError: Boolean,
    showPassword: Boolean,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
) {
    val supportingText = if (isError) {
        @Composable {
            Text(
                text = stringResource(Res.string.confirm_password_error_text)
            )
        }
    } else null

    BaseTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        label = {
            Text(stringResource(Res.string.confirm_password_label_text))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Key,
                contentDescription = null
            )
        },
        supportingText = supportingText,
        isError = isError,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        singleLine = true
    )
}