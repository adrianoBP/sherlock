package com.example.mircius.fingerprintauth;

import android.app.AlertDialog.Builder;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.C0251a;
import android.support.v4.app.aa.C0344c;
import android.support.v4.app.ad;
import android.support.v4.p023a.C0337a;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.C0612a;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.C0996a;
import com.google.android.gms.ads.C1021c.C1018a;
import com.google.android.gms.ads.C1027g;
import com.p046b.p047a.p048a.p049a.C0825h;
import com.p046b.p047a.p048a.p049a.C1654c;
import com.p046b.p047a.p048a.p049a.C1654c.C0817b;
import ro.andreimircius.remotefingerauth.R;

public class MainActivity extends C2089c implements C0817b {
    /* renamed from: A */
    private AdView f11169A = null;
    /* renamed from: B */
    private C1027g f11170B = null;
    /* renamed from: C */
    private final BroadcastReceiver f11171C = new C09171(this);
    /* renamed from: D */
    private C1654c f11172D;
    /* renamed from: m */
    private NavigationView f11173m = null;
    /* renamed from: n */
    private DrawerLayout f11174n;
    /* renamed from: o */
    private TextView f11175o;
    /* renamed from: p */
    private CryptoObject f11176p;
    /* renamed from: q */
    private FingerprintManager f11177q;
    /* renamed from: r */
    private KeyguardManager f11178r;
    /* renamed from: s */
    private C0966m f11179s = null;
    /* renamed from: t */
    private CancellationSignal f11180t;
    /* renamed from: u */
    private int f11181u = 1;
    /* renamed from: v */
    private String f11182v = "notifications_channel";
    /* renamed from: w */
    private int f11183w = 255;
    /* renamed from: x */
    private boolean f11184x;
    /* renamed from: y */
    private byte[] f11185y = null;
    /* renamed from: z */
    private boolean f11186z = false;

    /* renamed from: com.example.mircius.fingerprintauth.MainActivity$1 */
    class C09171 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ MainActivity f2917a;

        C09171(MainActivity mainActivity) {
            this.f2917a = mainActivity;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.app.example.MyServiceClass.STOP")) {
                this.f2917a.m17386p();
                this.f2917a.m17382l();
                if (this.f2917a.f11186z) {
                    this.f2917a.finish();
                }
            }
        }
    }

    /* renamed from: com.example.mircius.fingerprintauth.MainActivity$2 */
    class C09182 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ MainActivity f2918a;

        C09182(MainActivity mainActivity) {
            this.f2918a = mainActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f2918a.m17386p();
        }
    }

    /* renamed from: com.example.mircius.fingerprintauth.MainActivity$4 */
    class C09214 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ MainActivity f2925a;

        C09214(MainActivity mainActivity) {
            this.f2925a = mainActivity;
        }

        public void run() {
            this.f2925a.m17383m();
        }
    }

    /* renamed from: com.example.mircius.fingerprintauth.MainActivity$7 */
    class C09227 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ MainActivity f2926a;

        C09227(MainActivity mainActivity) {
            this.f2926a = mainActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f2926a.openScan(null);
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.example.mircius.fingerprintauth.MainActivity$8 */
    class C09238 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ MainActivity f2927a;

        C09238(MainActivity mainActivity) {
            this.f2927a = mainActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.example.mircius.fingerprintauth.MainActivity$9 */
    class C09249 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ MainActivity f2928a;

        C09249(MainActivity mainActivity) {
            this.f2928a = mainActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f2928a.openScan(null);
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.example.mircius.fingerprintauth.MainActivity$5 */
    class C16925 extends C0996a {
        /* renamed from: a */
        final /* synthetic */ MainActivity f7826a;

        C16925(MainActivity mainActivity) {
            this.f7826a = mainActivity;
        }

        /* renamed from: a */
        public void mo810a() {
            this.f7826a.m17379d(true);
        }
    }

    /* renamed from: com.example.mircius.fingerprintauth.MainActivity$6 */
    class C16936 implements C0251a {
        /* renamed from: a */
        final /* synthetic */ MainActivity f7827a;

        C16936(MainActivity mainActivity) {
            this.f7827a = mainActivity;
        }

        /* renamed from: a */
        public boolean mo809a(MenuItem menuItem) {
            menuItem.setChecked(true);
            CharSequence valueOf = String.valueOf(menuItem.getTitle());
            TextUtils.equals(valueOf, "Unlock");
            if (TextUtils.equals(valueOf, "My Accounts")) {
                this.f7827a.openAccounts(null);
            }
            if (TextUtils.equals(valueOf, "Scan")) {
                this.f7827a.openScan(null);
            }
            if (TextUtils.equals(valueOf, "Settings")) {
                this.f7827a.openSettings(null);
            }
            if (TextUtils.equals(valueOf, "Go PRO!")) {
                this.f7827a.openPro(null);
            }
            this.f7827a.f11174n.m2039b();
            return true;
        }
    }

    /* renamed from: c */
    private void m17376c(boolean z) {
        ViewPropertyAnimator animate;
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.cancelButtonLogon);
        float applyDimension = TypedValue.applyDimension(1, 82.0f, getResources().getDisplayMetrics());
        if (!z) {
            animate = floatingActionButton.animate();
        } else if (C0941d.m3879a()) {
            animate = floatingActionButton.animate();
            applyDimension = -applyDimension;
        } else {
            return;
        }
        animate.translationX(applyDimension).setDuration(1000).start();
    }

    /* renamed from: d */
    private void m17379d(boolean r4) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:6:0x0032 in {2, 4, 5} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1699113578.run(Unknown Source)
*/
        /*
        r3 = this;
        r0 = 2131296303; // 0x7f09002f float:1.8210519E38 double:1.0530002844E-314;
        r0 = r3.findViewById(r0);
        r0 = (android.support.design.widget.FloatingActionButton) r0;
        r3 = r3.getResources();
        r3 = r3.getDisplayMetrics();
        r1 = 1;
        r2 = 1112539136; // 0x42500000 float:52.0 double:5.496673668E-315;
        r3 = android.util.TypedValue.applyDimension(r1, r2, r3);
        r1 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r4 == 0) goto L_0x002d;
    L_0x001c:
        r4 = r0.animate();
        r3 = -r3;
    L_0x0021:
        r3 = r4.translationY(r3);
        r3 = r3.setDuration(r1);
        r3.start();
        return;
    L_0x002d:
        r4 = r0.animate();
        goto L_0x0021;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.mircius.fingerprintauth.MainActivity.d(boolean):void");
    }

    /* renamed from: k */
    private void m17381k() {
        Intent intent = new Intent();
        intent.setAction("com.app.example.MyServiceClass.STOP");
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 1073741824);
        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.setFlags(131072);
        ad.m1191a((Context) this).m1196a(this.f11183w, new C0344c(this, this.f11182v).m1155a((int) R.drawable.ic_fingerprint_black_24dp).m1160a((CharSequence) "Unlock initiated").m1164b((CharSequence) "Waiting for the target computer...").m1163b(0).m1156a(R.drawable.ic_launcher_background, "Cancel", broadcast).m1158a(PendingIntent.getActivity(this, 0, intent2, 1073741824)).m1165b(true).m1162a(true).m1154a());
    }

    /* renamed from: l */
    private void m17382l() {
        ad.m1191a((Context) this).m1195a(this.f11183w);
    }

    /* renamed from: m */
    private void m17383m() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1699113578.run(Unknown Source)
*/
        /*
        r4 = this;
        r0 = r4.isFinishing();
        if (r0 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = r4.f11179s;
        if (r0 != 0) goto L_0x000c;
    L_0x000b:
        return;
    L_0x000c:
        r0 = r4.getApplicationContext();
        r0 = android.preference.PreferenceManager.getDefaultSharedPreferences(r0);
        r1 = "uuid_enc";	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0 = r0.contains(r1);	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        if (r0 != 0) goto L_0x002c;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
    L_0x001c:
        com.example.mircius.fingerprintauth.C0968o.m3925a();	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0 = r4.f11175o;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r1 = "Initial configuration required. Simply scan your finger once to generate unique keys.";	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0.setText(r1);	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0 = 1;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0 = com.example.mircius.fingerprintauth.C0968o.m3924a(r4, r0);	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        goto L_0x0038;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
    L_0x002c:
        r0 = r4.f11175o;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r1 = "Scan your fingerprint to unlock your selected account!";	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0.setText(r1);	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0 = 0;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0 = com.example.mircius.fingerprintauth.C0968o.m3924a(r4, r0);	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
    L_0x0038:
        if (r0 == 0) goto L_0x005f;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
    L_0x003a:
        r1 = new android.hardware.fingerprint.FingerprintManager$CryptoObject;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r1.<init>(r0);	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r4.f11176p = r1;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0 = r4.f11180t;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0 = r0.isCanceled();	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        if (r0 == 0) goto L_0x0050;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
    L_0x0049:
        r0 = new android.os.CancellationSignal;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0.<init>();	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r4.f11180t = r0;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
    L_0x0050:
        r0 = r4.f11179s;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r1 = r4.f11177q;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r2 = r4.f11176p;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r3 = r4.f11180t;	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        r0.m3918a(r1, r2, r3);	 Catch:{ KeyPermanentlyInvalidatedException -> 0x005c }
        return;
    L_0x005c:
        r4.m17384n();
    L_0x005f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.mircius.fingerprintauth.MainActivity.m():void");
    }

    /* renamed from: n */
    private void m17384n() {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.clear();
        edit.putInt("savedComputers", 0);
        edit.apply();
        this.f11175o.setText("Fingerprint change detected. For security reasons, all accounts have been invalidated and the keys have to be recreated. The app will be restarted in 10 seconds...");
        new Handler().postDelayed(new Runnable(this) {
            /* renamed from: a */
            final /* synthetic */ MainActivity f2914a;

            {
                this.f2914a = r1;
            }

            public void run() {
                this.f2914a.recreate();
            }
        }, 10000);
    }

    /* renamed from: o */
    private void m17385o() {
        TextView textView;
        CharSequence charSequence;
        this.f11179s = null;
        if (VERSION.SDK_INT >= 23) {
            this.f11178r = (KeyguardManager) getSystemService("keyguard");
            this.f11177q = (FingerprintManager) getSystemService("fingerprint");
            this.f11175o = (TextView) findViewById(R.id.textview);
            if (!this.f11177q.isHardwareDetected()) {
                textView = this.f11175o;
                charSequence = "Your device doesn't support fingerprint authentication";
            } else if (C0337a.m1120a((Context) this, "android.permission.USE_FINGERPRINT") != 0) {
                textView = this.f11175o;
                charSequence = "Please enable the fingerprint permission";
            } else if (!this.f11178r.isKeyguardSecure()) {
                textView = this.f11175o;
                charSequence = "Please enable lockscreen security in your device's Settings";
            } else if (this.f11177q.hasEnrolledFingerprints()) {
                if (this.f11179s == null) {
                    this.f11179s = new C0966m(this);
                }
                this.f11180t = new CancellationSignal();
                return;
            } else {
                textView = this.f11175o;
                charSequence = "No fingerprint configured. Please register at least one fingerprint in your device's Settings";
            }
        } else {
            textView = this.f11175o;
            charSequence = "Android 6.0 or higher required in order to use this app.";
        }
        textView.setText(charSequence);
    }

    /* renamed from: p */
    private void m17386p() {
        C0941d.m3887b();
        m17376c(false);
    }

    /* renamed from: q */
    private void m17387q() {
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(this.f11182v, "Fingerprint Channel", 3);
            notificationChannel.setDescription("Channel required for Fingerprint Authenticator notifications");
            notificationChannel.enableVibration(false);
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
        }
    }

    /* renamed from: r */
    private void m17388r() {
        this.f11172D = C1654c.m10247a((Context) this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq1nguc6FmMQ8kYrQ12Mrx2Qy1C+rOqV2R/PzYRYlFu0hKJ++fAfe/51A0nPq0MJBQN6QfTzlzu22yPplQt+4cL17Zx6Qd5mxeuXIP7TUSFQPHCMKeJo/x/JYqf6qpMfbiTlDiLJv0jMD+gCLv7iZeoPG4T2Lz+C17ZnAd15ziFC9xxy75Mq2KUnlp1j5BbHltKb7hexUlaLYCpDqiZpfdWjupAe3IxOPVJ9qI/3wgkpqU5BwTbslPxzbaEXAOVopKYfMC3kWKyN1ImaWSejUwYRyjnrn6IBTBRj0EMnXbbbp87qJCwepJPwJartW1th62DMmgbsFfIpurjhY0w/XCQIDAQAB", (C0817b) this);
        this.f11172D.mo757c();
    }

    /* renamed from: s */
    private void m17389s() {
        if (this.f11172D != null) {
            this.f11172D.m10273d();
        }
    }

    /* renamed from: a */
    public void mo2755a(int i, Throwable th) {
    }

    /* renamed from: a */
    public void mo2765a(Boolean bool) {
        m17382l();
        if (this.f11170B != null && this.f11170B.m4039a()) {
            this.f11170B.m4040b();
        }
        this.f11185y = null;
        m17376c(false);
        if (this.f11186z) {
            finish();
        }
    }

    /* renamed from: a */
    public void mo2756a(String str, C0825h c0825h) {
    }

    /* renamed from: b */
    public void mo2757b() {
        Log.v("BILLING", "initialized");
        if (this.f11172D != null) {
            if (this.f11172D.m10268a("pro_upgrade")) {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (!defaultSharedPreferences.contains("shownProDialog")) {
                    new Builder(this, this.f11184x ? 16974374 : 16974394).setTitle("PRO purchase").setMessage("\nThank you for upgrading to PRO and supporting the app!\n\nAll ads are now disabled and the specified features have been activated. I hope that you will find them useful.\n\nEnjoy!").setPositiveButton("OK", null).show();
                    Editor edit = defaultSharedPreferences.edit();
                    edit.putBoolean("shownProDialog", true);
                    edit.apply();
                }
            } else {
                this.f11172D.m10276f();
                this.f11169A = (AdView) findViewById(R.id.mainAdView);
                this.f11169A.mo835a(new C1018a().m4018a());
                this.f11169A.setAdListener(new C16925(this));
                this.f11170B = new C1027g(this);
                this.f11170B.m4037a("ca-app-pub-8923166898670617/4296215656");
                this.f11170B.m4034a(new C1018a().m4018a());
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: b */
    public void mo2766b(boolean r11) {
        /*
        r10 = this;
        if (r11 == 0) goto L_0x0006;
    L_0x0002:
        r10.m17384n();
        return;
    L_0x0006:
        r11 = r10.f11179s;
        r11 = r11.m3917a();
        r10.f11181u = r11;
        r11 = r10.f11181u;
        r0 = 0;
        switch(r11) {
            case 0: goto L_0x002f;
            case 1: goto L_0x0026;
            case 2: goto L_0x001c;
            case 3: goto L_0x0015;
            default: goto L_0x0014;
        };
    L_0x0014:
        return;
    L_0x0015:
        r11 = r10.f11179s;
        r11 = r11.m3919b();
        goto L_0x001e;
    L_0x001c:
        r11 = "Authentication failed";
    L_0x001e:
        r10 = android.widget.Toast.makeText(r10, r11, r0);
        r10.show();
        return;
    L_0x0026:
        r11 = r10.f11180t;
        r11 = r11.isCanceled();
        if (r11 != 0) goto L_0x0132;
    L_0x002e:
        goto L_0x0015;
    L_0x002f:
        r11 = r10.f11179s;
        r11 = r11.m3920c();
        r10.f11185y = r11;
        r11 = r10.f11185y;
        if (r11 == 0) goto L_0x011a;
    L_0x003b:
        r11 = r10.getApplicationContext();
        r11 = android.preference.PreferenceManager.getDefaultSharedPreferences(r11);
        r1 = "default_comp";
        r1 = r11.contains(r1);
        if (r1 != 0) goto L_0x004f;
    L_0x004b:
        r11 = "No default account set!";
        goto L_0x011c;
    L_0x004f:
        r1 = "default_comp";
        r2 = -1;
        r1 = r11.getInt(r1, r2);
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "computer_";
        r2.append(r3);
        r3 = java.lang.String.valueOf(r1);
        r2.append(r3);
        r3 = "_id";
        r2.append(r3);
        r2 = r2.toString();
        r3 = "null";
        r9 = r11.getString(r2, r3);
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "computer_";
        r2.append(r3);
        r3 = java.lang.String.valueOf(r1);
        r2.append(r3);
        r3 = "_wol";
        r2.append(r3);
        r2 = r2.toString();
        r7 = r11.getBoolean(r2, r0);
        r0 = "";
        if (r7 == 0) goto L_0x00ba;
    L_0x0098:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = "computer_";
        r0.append(r2);
        r1 = java.lang.String.valueOf(r1);
        r0.append(r1);
        r1 = "_mac";
        r0.append(r1);
        r0 = r0.toString();
        r1 = "02:00:00:00:00:00";
        r11 = r11.getString(r0, r1);
        r8 = r11;
        goto L_0x00bb;
    L_0x00ba:
        r8 = r0;
    L_0x00bb:
        r11 = com.example.mircius.fingerprintauth.C0941d.m3879a();
        if (r11 != 0) goto L_0x00d6;
    L_0x00c1:
        r11 = 54;
        com.example.mircius.fingerprintauth.C0941d.m3877a(r10, r11, r7, r8, r9);
        r11 = new android.os.Handler;
        r11.<init>();
        r0 = new com.example.mircius.fingerprintauth.MainActivity$12;
        r0.<init>(r10);
        r1 = 750; // 0x2ee float:1.051E-42 double:3.705E-321;
        r11.postDelayed(r0, r1);
        goto L_0x0123;
    L_0x00d6:
        r11 = new android.app.AlertDialog$Builder;
        r0 = r10.f11184x;
        if (r0 == 0) goto L_0x00e0;
    L_0x00dc:
        r0 = 16974374; // 0x1030226 float:2.4062441E-38 double:8.386455E-317;
        goto L_0x00e3;
    L_0x00e0:
        r0 = 16974394; // 0x103023a float:2.4062497E-38 double:8.386465E-317;
    L_0x00e3:
        r11.<init>(r10, r0);
        r0 = "Operation in progress";
        r11 = r11.setTitle(r0);
        r0 = "Do you want to cancel the current operation and start the new one?";
        r11 = r11.setMessage(r0);
        r0 = "YES";
        r1 = new com.example.mircius.fingerprintauth.MainActivity$3;
        r4 = r1;
        r5 = r10;
        r6 = r10;
        r4.<init>(r5, r6, r7, r8, r9);
        r11 = r11.setPositiveButton(r0, r1);
        r0 = "CANCEL ONGOING";
        r1 = new com.example.mircius.fingerprintauth.MainActivity$2;
        r1.<init>(r10);
        r11 = r11.setNeutralButton(r0, r1);
        r0 = "NO";
        r1 = new com.example.mircius.fingerprintauth.MainActivity$13;
        r1.<init>(r10);
        r11 = r11.setNegativeButton(r0, r1);
        r11.show();
        goto L_0x0123;
    L_0x011a:
        r11 = "Configuration done!";
    L_0x011c:
        r11 = android.widget.Toast.makeText(r10, r11, r0);
        r11.show();
    L_0x0123:
        r11 = new android.os.Handler;
        r11.<init>();
        r0 = new com.example.mircius.fingerprintauth.MainActivity$4;
        r0.<init>(r10);
        r1 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r11.postDelayed(r0, r1);
    L_0x0132:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.mircius.fingerprintauth.MainActivity.b(boolean):void");
    }

    public void c_() {
    }

    /* renamed from: d */
    public void mo2767d(int i) {
        m17376c(false);
        if (i == -1) {
            m17382l();
            if (this.f11186z) {
                finish();
            }
            return;
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int i2 = defaultSharedPreferences.getInt("default_comp", -1);
        int i3 = defaultSharedPreferences.getInt("default_acc", -1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("computer_");
        stringBuilder.append(String.valueOf(i2));
        stringBuilder.append("_acc_");
        stringBuilder.append(String.valueOf(i3));
        C0941d.m3878a(this, '6', this.f11185y, defaultSharedPreferences.getString(stringBuilder.toString(), "null"), null, null);
    }

    public void onBackPressed() {
        if (C0941d.m3879a() && C0941d.m3895d()) {
            this.f11186z = true;
            moveTaskToBack(true);
            return;
        }
        finish();
    }

    public void onCancelClick(View view) {
        m17386p();
    }

    protected void onCreate(Bundle bundle) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.f11184x = defaultSharedPreferences.getBoolean("darkModePreference", false);
        setTheme(this.f11184x ? R.style.AppThemeDark : R.style.AppThemeLight);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_logon);
        m17387q();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.app.example.MyServiceClass.STOP");
        registerReceiver(this.f11171C, intentFilter);
        ((ImageView) findViewById(R.id.imageView)).setImageResource(this.f11184x ? R.drawable.ic_fingerprint_grey : R.drawable.ic_fingerprint_dark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle((CharSequence) "Unlock");
        m17243a(toolbar);
        this.f11174n = (DrawerLayout) findViewById(R.id.drawer_layout);
        C0612a g = mo2718g();
        g.mo453b(true);
        g.mo445a((int) R.drawable.ic_menu_white);
        this.f11173m = (NavigationView) findViewById(R.id.nav_view);
        this.f11173m.setNavigationItemSelectedListener(new C16936(this));
        if (!defaultSharedPreferences.contains("savedComputers")) {
            Editor edit = defaultSharedPreferences.edit();
            edit.putInt("savedComputers", 0);
            edit.apply();
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.cancelButtonLogon);
        floatingActionButton.setVisibility(8);
        floatingActionButton.setVisibility(0);
        floatingActionButton.setVisibility(8);
        m17376c(true);
        m17376c(false);
        floatingActionButton.setVisibility(0);
        if (!defaultSharedPreferences.getBoolean("shownWelcomeDialog", false)) {
            CharSequence spannableStringBuilder = new SpannableStringBuilder("First of all, make sure that you have installed the Windows module found in the app's description. This app cannot work without it.\n\nWhen you are ready, go to the Scan menu to save your computer in the app.");
            spannableStringBuilder.setSpan(new StyleSpan(1), 14, 97, 33);
            new Builder(this, this.f11184x ? 16974374 : 16974394).setTitle("Welcome!").setMessage(spannableStringBuilder).setPositiveButton("OK", new C09238(this)).setNeutralButton("SCAN", new C09227(this)).show();
            Editor edit2 = defaultSharedPreferences.edit();
            edit2.putBoolean("shownWelcomeDialog", true);
            edit2.apply();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.f11171C);
        m17389s();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        int size = this.f11173m.getMenu().size();
        for (int i = 0; i < size; i++) {
            this.f11173m.getMenu().getItem(i).setChecked(false);
        }
        this.f11174n.m2051e(8388611);
        return true;
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!defaultSharedPreferences.getBoolean("shownWelcomeDialog", false)) {
            CharSequence spannableStringBuilder = new SpannableStringBuilder("First of all, make sure that you have installed the Windows module found in the app's description. This app cannot work without it.\n\nWhen you are ready, go to the Scan menu to save your computer in the app.");
            spannableStringBuilder.setSpan(new StyleSpan(1), 14, 97, 33);
            new Builder(this, this.f11184x ? 16974374 : 16974394).setTitle("Welcome!").setMessage(spannableStringBuilder).setPositiveButton("OK", new OnClickListener(this) {
                /* renamed from: a */
                final /* synthetic */ MainActivity f2913a;

                {
                    this.f2913a = r1;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setNeutralButton("SCAN", new C09249(this)).show();
            Editor edit = defaultSharedPreferences.edit();
            edit.putBoolean("shownWelcomeDialog", true);
            edit.apply();
        }
        if (defaultSharedPreferences.getBoolean("shouldMainReset", false)) {
            edit = defaultSharedPreferences.edit();
            edit.remove("shouldMainReset");
            edit.apply();
            recreate();
        }
        if (defaultSharedPreferences.getBoolean("darkModePreference", false) != this.f11184x) {
            recreate();
        }
        if (this.f11180t == null || this.f11180t.isCanceled()) {
            m17385o();
            m17383m();
        }
        m17382l();
        this.f11186z = false;
        if (this.f11172D == null || !this.f11172D.m10275e()) {
            m17388r();
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.f11180t != null) {
            this.f11180t.cancel();
        }
        if (C0941d.m3879a() && C0941d.m3895d()) {
            m17381k();
        }
    }

    public void openAccounts(View view) {
        startActivity(new Intent(this, AccountsActivity.class));
    }

    public void openPro(View view) {
        startActivity(new Intent(this, ProActivity.class));
    }

    public void openScan(View view) {
        startActivity(new Intent(this, ScanActivity.class));
    }

    public void openSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
