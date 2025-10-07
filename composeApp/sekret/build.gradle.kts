plugins {
  id("com.android.library")
  kotlin("multiplatform")
}

kotlin {
  androidTarget()
  iosArm64 {
    binaries {
      sharedLib()
    }
  }
  iosSimulatorArm64 {
    binaries {
      sharedLib()
    }
  }
  iosX64 {
    binaries {
      sharedLib()
    }
  }
  androidNativeX86 {
    binaries {
      sharedLib()
    }
  }
  androidNativeX64 {
    binaries {
      sharedLib {
        // Workaround for android 16kb page size, currently not supported on this target
        linkerOpts += listOf("-Wl,-z,max-page-size=16384", "-Wl,-z,common-page-size=16384", "-v")
      }
    }
  }
  androidNativeArm32 {
    binaries {
      sharedLib()
    }
  }
  androidNativeArm64 {
    binaries {
      sharedLib()
    }
  }

  applyDefaultHierarchyTemplate()

  sourceSets {
    commonMain.dependencies {
      api("dev.datlag.sekret:sekret:2.2.10")
    }

    val jniNativeMain by creating {
      nativeMain.orNull?.let { dependsOn(it) } ?: dependsOn(commonMain.get())
      androidNativeMain.orNull?.dependsOn(this)
      linuxMain.orNull?.dependsOn(this)
      mingwMain.orNull?.dependsOn(this)
      macosMain.orNull?.dependsOn(this)
    }

    val jniMain by creating {
      dependsOn(commonMain.get())
      androidMain.orNull?.dependsOn(this)
      jvmMain.orNull?.dependsOn(this)
    }
  }
}
android {
  namespace = "com.juanpablo0612.agrostaff"
}
