import React, { createContext, useContext, useState, type ReactElement } from 'react';

const TabsContext = createContext<{
  activeTab: string;
  setActiveTab: (id: string) => void;
} | null>(null);

interface TabProps {
  id: string;
  label: string;
  icon?: string;
  children: React.ReactNode;
}

export function Tab({ children }: TabProps) {
  const context = useContext(TabsContext);
  if (!context) throw new Error('<Tab /> doit être utilisé dans <Tabs />');

  return <>{children}</>;
}

interface TabsProps {
  children: React.ReactNode;
  defaultActiveId?: string;
}

export function Tabs({ children, defaultActiveId }: TabsProps) {
  const validChildren = React.Children.toArray(children).filter(
    (child): child is ReactElement<TabProps> => React.isValidElement(child)
  );

  const [activeTab, setActiveTab] = useState(
    defaultActiveId || validChildren[0]?.props.id || ''
  );

  return (
    <TabsContext.Provider value={{ activeTab, setActiveTab }}>
      <div className="space-y-6 w-full">
        <div className="flex border-b border-slate-800 w-full overflow-x-auto">
          {validChildren.map((child) => {
            const { id, label, icon } = child.props;
            const isActive = activeTab === id;

            return (
              <button
                key={id}
                onClick={() => setActiveTab(id)}
                className={`px-6 py-3 text-sm font-medium border-b-2 whitespace-nowrap transition-colors duration-200 ${
                  isActive
                    ? 'border-indigo-500 text-indigo-400'
                    : 'border-transparent text-slate-400 hover:text-slate-200'
                }`}
              >
                {icon && <span className={`${icon} mr-2`} />}
                {label}
              </button>
            );
          })}
        </div>

        <div className="w-full">
          {validChildren.find((child) => child.props.id === activeTab)?.props.children}
        </div>
      </div>
    </TabsContext.Provider>
  );
}
