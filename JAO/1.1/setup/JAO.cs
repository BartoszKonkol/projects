using System;
using System.IO;
using System.Net;
using System.Reflection;
using System.Windows.Forms;
using System.Collections.Generic;
using System.Runtime.InteropServices;

namespace JAO_Setup
{

    static class JAO
    {

        public const string Title = "Java Application Open", Version = "1.1.1", ISA = "x64";
        public static Form Window = new Window();
        public static Taskbar Taskbar = new Taskbar();
        public static volatile List<string> FileDownloadLast = new List<string>();
        public static volatile int ProgressPercent = 0;

        [STAThread]
        static void Main()
        {
            try
            {
                Application.EnableVisualStyles();
                ((Window) Window).InitializeWindow();
                Application.Run(Window);
            }
            catch(Exception e)
            {
                CriticalError(e);
            }
        }

        public static Assembly GiveAssembly(string library)
        {
            return Assembly.LoadFrom(Path.GetFullPath(library + ".dll"));
        }

        public static void CriticalError(Exception e)
        {
            Taskbar.Progress(Taskbar.ProgressState.Error);
            if(MessageBox.Show("Wystąpił niezdefiniowany błąd krytyczny!\nPowód:\n    " + e.Message.Replace("\n", "\n    ") + "\n\nSpróbuj uruchomić instalator ponownie.\nJeśli błąd nie ustąpi, zgłoś zaistniałą sytuację do Polish Games Studio.\n\nCzy chcesz uzyskać opis techniczny powstałego błędu?", JAO.Title, MessageBoxButtons.YesNo, MessageBoxIcon.Stop) == DialogResult.Yes)
                MessageBox.Show("Informacje techniczne dotyczące wystąpienia błędu krytycznego:\n\n\n~ !Message\n" + e.Message + "\n\n~ !StackTrace\n" + e.StackTrace + "\n\n~ !Source\n" + e.Source + "\n\n~ !TargetSite\n" + e.TargetSite + "\n\n~ !.GetType.FullName\n" + e.GetType().FullName + "\n\n~ ?DateTime.Now..ToString(string)\n" + DateTime.Now.ToString("yyyy-MM-dd HH:mm (dddd)") + "\n\n~ ?Directory..GetCurrentDirectory\n" + Directory.GetCurrentDirectory() + "\n\n~ ?Directory..GetDirectories(*'.GetCurrentDirectory).Length\n" + Directory.GetDirectories(Directory.GetCurrentDirectory()).Length + "\n\n~ ?Directory..GetFiles(*'.GetCurrentDirectory).Length\n" + Directory.GetFiles(Directory.GetCurrentDirectory()).Length, JAO.Title, MessageBoxButtons.OK, MessageBoxIcon.Information);
            Environment.Exit(1);
        }

        public static void DirectoryDelete(string directory)
        {
            if(Directory.GetFiles(directory).Length == 0 && Directory.GetDirectories(directory).Length == 0)
            {
                Directory.Delete(directory);
                DirectoryDelete(Path.GetDirectoryName(directory));
            }
        }

        public static string FileDownload(string file, ProgressBar progress, float percent)
        {
            try
            {
                WebClient web = new WebClient();
                web.DownloadProgressChanged += (object sender, DownloadProgressChangedEventArgs args) => { progress.Value = ProgressPercent + (int)Math.Round(args.ProgressPercentage * (percent / 100)); };
                web.DownloadFileCompleted += (object sendr, System.ComponentModel.AsyncCompletedEventArgs args) => {FileDownloadLast.Add(file); ProgressPercent += (int)Math.Round(percent); progress.Value = ProgressPercent;};
                web.DownloadFileAsync(new Uri("http://www.res.jao.polishgames.net/" + file), file);
            }
            catch(WebException e)
            {
                Taskbar.Progress(Taskbar.ProgressState.Error);
                DialogResult result = MessageBox.Show("Wystąpił błąd podczas próby pobierania pliku '" + file + "':\n    " + e.Message + "\n\nSprawdź swoje połączenie internetowe i spróbuj ponownie.\nJeśli błąd nie ustąpi, zgłoś zaistniałą sytuację do Polish Games Studio.", JAO.Title, MessageBoxButtons.OK, MessageBoxIcon.Stop);
                if(result == DialogResult.OK)
                    Environment.Exit(2);
            }
            return file;
        }

        public static string ZipExtract(string file, string directory, Assembly assembly)
        {
            if(directory == null)
                directory = ".";
            if(directory.Length > 0)
                Directory.CreateDirectory(directory);
            Type type = assembly.GetType("Ionic.Zip.ZipFile");
            Type typeEnum = assembly.GetType("Ionic.Zip.ExtractExistingFileAction");
            object zip = Activator.CreateInstance(type, new object[] {file += "-" + ISA + ".zip"});
            type.GetMethod("ExtractAll", new Type[]{Type.GetType("System.String"), typeEnum}).Invoke(zip, new object[]{directory, typeEnum.GetEnumValues().GetValue(1)});
            type.GetMethod("Dispose").Invoke(zip, null);
            return file;
        }

    }

}
