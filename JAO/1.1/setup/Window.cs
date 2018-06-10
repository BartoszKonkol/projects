using System;
using System.IO;
using System.Net;
using System.Threading;
using System.Reflection;
using System.Windows.Forms;
using Microsoft.Win32;

namespace JAO_Setup
{
    public partial class Window : Form
    {

        private Screen screen;
        private RegistryKey key;

        public void InitializeWindow()
        {
            this.screen = Screen.None;
            InitializeComponent();
            this.Text = JAO.Title + " (" + JAO.Version + ") – Setup";
            this.button1.Text = "Anuluj";
            this.key = Registry.LocalMachine.CreateSubKey("SOFTWARE\\JAO");
            if (this.key.GetValue("AppDir") == null)
                this.key.SetValue("AppDir", Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles) + "\\Polish Games Studio\\JAO");
            this.folderBrowserDialog1.SelectedPath = this.key.GetValue("AppDir").ToString();
            this.textBox1.Text = this.folderBrowserDialog1.SelectedPath;
            this.ScreenFirst();
        }

        protected override void OnFormClosing(FormClosingEventArgs args)
        {
            if(this.screen == Screen.Second)
                args.Cancel = true;
            else
            {
                JAO.Taskbar.Progress(Taskbar.ProgressState.Paused);
                DialogResult result = MessageBox.Show("Czy na pewno chcesz zakończyć działanie instalatora?", JAO.Title, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                JAO.Taskbar.Progress();
                switch(result)
                {
                    case DialogResult.Yes:
                        {
                            args.Cancel = false;
                        } break;
                    case DialogResult.No:
                        {
                            args.Cancel = true;
                        } break;
                }
            }
        }

        private void ScreenFirst()
        {
            this.screen = Screen.First;
            JAO.Taskbar.Urgency();
            this.groupBox1.Visible = true;
            this.button2.Text = "Zainstaluj";
            this.label1.Text = "Witamy w kreatorze instalacji systemu plików " + JAO.Title + " " + JAO.Version + "!";
            this.label2.Text = "Zaleca się zamknięcie innych uruchomionych programów przed rozpoczęciem procesu instalacji.\n\n\nWybierz przycisk " + this.button2.Text + ", aby rozpocząć tę część procesu instalacji, lub " + this.button1.Text + ", aby przerwać działanie kreatora. Pamiętaj, że nie będziesz mieć możliwości zatrzymania instalacji.\n\n\nMożesz zmienić główny katalog " + JAO.Title + ", korzystając z poniższego przycisku " + this.button3.Text + ".";
        }

        private void ScreenSecond()
        {
            this.screen = Screen.Second;
            JAO.Taskbar.Urgency();
            this.groupBox1.Visible = false;
            this.label3.Visible = true;
            this.progressBar1.Visible = true;
            this.button2.Text = "Czekaj...";
            this.button1.Enabled = false;
            this.button2.Enabled = false;
            this.label1.Text = "Instalacja";
            this.label3.Text = "Proszę czekać...";
            this.label2.Text = "\nTrwa instalacja systemu plików " + JAO.Title + " " + JAO.Version + "...\n\n" + this.label3.Text + "\n\n\nProces ten może potrwać od kilku sekund do kilkunastu minut, w zależności od specyfikacji używanego sprzętu, prędkości łącza internetowego oraz już istniejących składników.";
            JAO.Taskbar.OverlayIcon(68);
            string directory = this.folderBrowserDialog1.SelectedPath;
            Directory.CreateDirectory(directory);
            Directory.SetCurrentDirectory(directory);
            string fileZip = JAO.FileDownload("Ionic.Zip.dll", this.progressBar1, 5);
            string fileJAO = JAO.FileDownload("jao-001-" + JAO.ISA + ".zip", this.progressBar1, 30);
            string fileJVM = null;
            if(!new DirectoryInfo("JVM/" + JAO.ISA + "/jre1.7.0_80").Exists)
                fileJVM = JAO.FileDownload("jre-7u80-" + JAO.ISA + ".zip", this.progressBar1, 30);
            new Thread(() =>
            {
                try
                {
                    this.Invoke(new EventHandler(delegate {this.DownloadInfo(fileZip);}));
                    while (!JAO.FileDownloadLast.Exists(e => e.Equals(fileZip)))
                        Thread.Sleep(10);
                    Thread.Sleep(500);
                    this.Invoke(new EventHandler(delegate {this.DownloadInfo(fileJAO);}));
                    while (!JAO.FileDownloadLast.Exists(e => e.Equals(fileJAO)))
                        Thread.Sleep(10);
                    Thread.Sleep(500);
                    if (fileJVM != null)
                    {
                        this.Invoke(new EventHandler(delegate {this.DownloadInfo(fileJVM);}));
                        while (!JAO.FileDownloadLast.Exists(e => e.Equals(fileJVM)))
                            Thread.Sleep(10);
                    }
                    else
                    {
                        this.Invoke(new EventHandler(delegate {this.SkipInfo();}));
                        this.Invoke(new EventHandler(delegate {this.ProgressPercent(30);}));
                    }
                    Thread.Sleep(500);
                    String library = "Ionic.Zip";
                    this.Invoke(new EventHandler(delegate {this.ProgressInfo("Trwa przygotowywanie zasobów biblioteki '" + library + "'...");}));
                    Assembly assembly = JAO.GiveAssembly(library);
                    this.Invoke(new EventHandler(delegate {this.ProgressPercent(5);}));
                    Thread.Sleep(500);
                    if (fileJVM != null)
                    {
                        this.Invoke(new EventHandler(delegate {this.ExtractInfo(fileJVM);}));
                        JAO.ZipExtract("jre-7u80", "JVM/" + JAO.ISA, assembly);
                        RegistryKey keyJVM = this.key.CreateSubKey("JVM");
                        string value = "java";
                        keyJVM.SetValue(value, "jre");
                        value += "." + keyJVM.GetValue(value);
                        keyJVM.SetValue(value, JAO.ISA);
                        value += "." + keyJVM.GetValue(value);
                        keyJVM.SetValue(value, "7u80");
                        value += "." + keyJVM.GetValue(value);
                        keyJVM.SetValue(value, 0x0);
                        keyJVM.SetValue(value += ".dir", Path.GetFullPath("JVM/" + JAO.ISA + "/jre1.7.0_80"));
                        keyJVM.SetValue(value + ".bin", Path.GetFullPath(keyJVM.GetValue(value) + "/bin"));
                    }
                    else
                        this.Invoke(new EventHandler(delegate {this.SkipInfo();}));
                    this.Invoke(new EventHandler(delegate {this.ProgressPercent(15);}));
                    Thread.Sleep(500);
                    this.Invoke(new EventHandler(delegate {this.ExtractInfo(fileJAO);}));
                    JAO.ZipExtract("jao-001", null, assembly);
                    this.Invoke(new EventHandler(delegate {this.ProgressPercent(15);}));
                    Thread.Sleep(500);
                    this.Invoke(new EventHandler(delegate {this.ProgressInfo("Trwa kasowanie zbędnych plików...");}));
                    if(fileJVM != null)
                        File.Delete(fileJVM);
                    File.Delete(fileJAO);
                    Thread.Sleep(500);
                    this.Invoke(new EventHandler(delegate {this.ProgressPercent(100);}));
                    this.Invoke(new EventHandler(delegate {this.ProgressInfo("Ta część instalacji została pomyślnie ukończona!");}));
                    Thread.Sleep(500);
                    this.Invoke(new EventHandler(delegate {this.ScreenThird();}));
                }
                catch(Exception e)
                {
                    this.Invoke(new EventHandler(delegate {JAO.CriticalError(e);}));
                }
            }).Start();
            new Thread(() =>
            {
                try
                {
                    while(this.screen == Screen.Second)
                    {
                        this.Invoke(new EventHandler(delegate {JAO.Taskbar.Progress(this.progressBar1.Value);}));
                        Thread.Sleep(10);
                    }
                }
                catch(Exception e)
                {
                    this.Invoke(new EventHandler(delegate {JAO.CriticalError(e);}));
                }
            }).Start();
        }

        private void ScreenThird()
        {
            this.screen = Screen.Third;
            JAO.Taskbar.Urgency();
            this.label3.Visible = false;
            this.progressBar1.Visible = false;
            JAO.Taskbar.OverlayIcon();
            JAO.Taskbar.Progress();
            this.button2.Text = "Kontynuuj";
            this.button1.Enabled = true;
            this.button2.Enabled = true;
            this.label1.Text = this.label3.Text;
            this.label2.Text = "\nKreator zakończył wyodrębnianie wymaganych plików i składników " + JAO.Title + " oraz przygotował je do dalszego użytku.\n\n\nWybierz przycisk " + this.button2.Text + ", aby ukończyć instalacje systemu plików " + JAO.Title + " " + JAO.Version + " i uruchomić dalszy kreator, lub " + this.button1.Text + ", jeśli chcesz dokończyć proces w późniejszym terminie lub przerwać instalacje.";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            switch(this.screen)
            {
                case Screen.First:
                    {
                        this.ScreenSecond();
                    } break;
                case Screen.Second:
                    {
                        this.ScreenThird();
                    } break;
                case Screen.Third:
                    {
                        // TODO
                    } break;
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if(this.screen == Screen.First)
            {
                string directory = this.folderBrowserDialog1.SelectedPath;
                Directory.CreateDirectory(directory);
                JAO.Taskbar.Progress(Taskbar.ProgressState.Paused);
                this.folderBrowserDialog1.ShowDialog();
                JAO.Taskbar.Progress();
                JAO.DirectoryDelete(directory);
                this.key.SetValue("AppDir", this.textBox1.Text = this.folderBrowserDialog1.SelectedPath);
            }
        }

        private void DownloadInfo(string file)
        {
            this.ProgressInfo("Trwa pobieranie pliku '" + file + "'...");
        }

        private void ExtractInfo(string file)
        {
            this.ProgressInfo("Trwa wyodrębnianie archiwum ZIP z pliku '" + file + "'...");
        }

        private void SkipInfo()
        {
            this.ProgressInfo("Pomijanie instalacji już istniejących elementów...");
        }

        private void ProgressInfo(string text)
        {
            this.label3.Text = text;
        }

        private void ProgressPercent(int percent)
        {
            int value = this.progressBar1.Value + percent;
            if(value >= 100)
                this.progressBar1.Value = 100;
            else
                this.progressBar1.Value = value;
        }

        private enum Screen
        {
            None,
            First,
            Second,
            Third,
        }
    }
}
