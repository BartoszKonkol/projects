using System;
using System.Drawing;
using System.Runtime.InteropServices;

namespace JAO_Setup
{

    public class Taskbar
    {

        private static ITaskbar TaskBar;

        public Taskbar()
        {
            if(TaskBar == null)
            {
                TaskBar = (ITaskbar) new TaskbarClass();
                TaskBar.HrInit();
            }
        }

        [DllImport("shell32.dll")]
        [return: MarshalAs(UnmanagedType.SysUInt)]
        protected static extern IntPtr ExtractIcon(IntPtr hInst, string lpszExeFileName, int nIconIndex);

        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        protected static extern bool FlashWindowEx(ref FlashWindow fw);

        [StructLayout(LayoutKind.Sequential)]
        protected struct FlashWindow
        {
            public UInt32 cbSize;
            public IntPtr hwnd;
            public UInt32 dwFlags;
            public UInt32 uCount;
            public UInt32 dwTimeout;
        }

        protected static bool FlashWindowEx()
        {
            FlashWindow fw = new FlashWindow();
            fw.cbSize = Convert.ToUInt32(Marshal.SizeOf(fw));
            fw.hwnd = JAO.Window.Handle;
            fw.dwFlags = 0xF;
            fw.uCount = 1;
            fw.dwTimeout = 0;
            return FlashWindowEx(ref fw);
        }

        public Taskbar Progress(int percent)
        {
            TaskBar.SetProgressValue(JAO.Window.Handle, Convert.ToUInt32(percent), Convert.ToUInt32(100));
            return this;
        }

        public Taskbar Progress(ProgressState state)
        {
            TaskBar.SetProgressState(JAO.Window.Handle, Convert.ToUInt16(state == ProgressState.Indeterminate ? 0x1 : state == ProgressState.Normal ? 0x2 : state == ProgressState.Paused ? 0x8 : state == ProgressState.Error ? 0x4 : 0x0));
            return this;
        }

        public Taskbar Progress(int percent, ProgressState state)
        {
            this.Progress(percent);
            this.Progress(state);
            return this;
        }

        public Taskbar Progress()
        {
            this.Progress(0, ProgressState.Normal);
            return this;
        }

        protected Taskbar OverlayIcon(IntPtr icon)
        {
            TaskBar.SetOverlayIcon(JAO.Window.Handle, icon, "");
            return this;
        }

        public Taskbar OverlayIcon(Icon icon)
        {
            this.OverlayIcon(icon.Handle);
            return this;
        }

        public Taskbar OverlayIcon(string file, int index)
        {
            this.OverlayIcon(ExtractIcon(IntPtr.Zero, file, index));
            return this;
        }

        public Taskbar OverlayIcon(int index)
        {
            this.OverlayIcon(Environment.GetFolderPath(Environment.SpecialFolder.SystemX86) + "\\shell32.dll", index);
            return this;
        }

        public Taskbar OverlayIcon()
        {
            this.OverlayIcon((IntPtr) null);
            return this;
        }

        public Taskbar Urgency()
        {
            FlashWindowEx();
            return this;
        }

        public enum ProgressState
        {
            Indeterminate,
            Normal,
            Paused,
            Error,
        }

    }

    [GuidAttribute("56FDF344-FD6D-11d0-958A-006097C9A090")]
    [ClassInterfaceAttribute(ClassInterfaceType.None)]
    [ComImportAttribute()]
    internal class TaskbarClass {}

    [GuidAttribute("c43dc798-95d1-4bea-9030-bb99e2983a1a")]
    [InterfaceTypeAttribute(ComInterfaceType.InterfaceIsIUnknown)]
    [ComImportAttribute()]
    internal interface ITaskbar
    {

        // 1
        [PreserveSig] void HrInit();

        // 1 (unknown)
        void AddTab();
        void DeleteTab();
        void ActivateTab();
        void SetActiveAlt();

        // 2 (unknown)
        void MarkFullscreenWindow();

        // 3
        [PreserveSig] void SetProgressValue(IntPtr hwnd, UInt32 ullCompleted, UInt32 ullTotal);
        [PreserveSig] void SetProgressState(IntPtr hwnd, UInt16 tbpFlags);

        // 3 (unknown)
        void RegisterTab();
        void UnregisterTab();
        void SetTabOrder();
        void SetTabActive();
        object ThumbBarAddButtons();
        object ThumbBarUpdateButtons();
        void ThumbBarSetImageList();

        // 3 (end)
        [PreserveSig] void SetOverlayIcon(IntPtr hwnd, IntPtr hIcon, [MarshalAs(UnmanagedType.LPWStr)] string pszDescription);

        // 3 (unknown end)
        void SetThumbnailTooltip();
        void SetThumbnailClip();

        // 4 (unknown)
        void SetTabProperties();

    }

}
