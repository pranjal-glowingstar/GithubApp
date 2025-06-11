package com.apps.assignment.common

object AppConstants {
    const val SHARED_PREF_NAME = "sharedPrefName"
    const val KEY_AUTH_TOKEN = "authToken"
    const val EMPTY = ""
    //this token is only available for 30 days with read access
    //make sure that your device date and time are correct and up to date
    //this token was generated on 11-June-2025
    //ideally, this should be saved somewhere secure and should be dynamic in nature. But since this is a test project, we can ignore for now
    //This is currently stored in Base 64 encrypted format
    const val KEY_GITHUB_AUTH_TOKEN = "Z2l0aHViX3BhdF8xMUFRTzdRUVkwM2s2WlBRMklZM1Y4X3NyM3BZTmtUZldabHlScUV1RmhaSHM3ZXNadm15TGJsTWRucVdiclpXTWFXUU1CRVZGTmVTQXc3SEty"
}